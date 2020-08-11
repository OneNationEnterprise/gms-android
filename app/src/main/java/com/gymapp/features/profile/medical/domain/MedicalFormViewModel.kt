package com.gymapp.features.profile.medical.domain

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.gym.type.ContentElementType
import com.apollographql.apollo.gym.type.CustomerMedicalForm
import com.apollographql.apollo.gym.type.CustomerMedicalFormField
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.UserRepositoryInterface
import com.gymapp.features.profile.medical.data.ContentElementDataMapper
import com.gymapp.features.profile.medical.data.MedicalFormListObject
import com.gymapp.features.profile.medical.data.items.CheckBoxItem
import com.gymapp.features.profile.medical.data.items.GroupTitleItem
import com.gymapp.features.profile.medical.data.items.SaveButtonItem
import com.gymapp.features.profile.medical.data.items.TextBoxItem
import com.gymapp.main.network.ApiManagerInterface

class MedicalFormViewModel(
    private val apiManagerInterface: ApiManagerInterface,
    private val userRepositoryInterface: UserRepositoryInterface
) : BaseViewModel() {

    val itemsForInMedicalFormAdapterList = MutableLiveData<ArrayList<MedicalFormListObject>>()
    val dismissLoadingState = MutableLiveData<Boolean>()
    val showErrorBanner = MutableLiveData<String>()
    val showSuccessBanner = MutableLiveData<Boolean>()
    val notifyActivityForSuspendFunctionScope = MutableLiveData<CustomerMedicalForm>()


    private val contentElementsFieldList = ArrayList<CustomerMedicalFormField>()
    private val contentElementDataMapper = ContentElementDataMapper()
    private var numberOfFormFields: Int = 0

    suspend fun fetchData() {
        val apiResponse = apiManagerInterface.getMedicalFormAsync().await()

        if (!apiResponse.errors.isNullOrEmpty() || apiResponse.data == null) {
            return
        }

        val medicalFormData = apiResponse.data!!.getMedicalForm

        val itemsForInMedicalFormAdapterList = ArrayList<MedicalFormListObject>()

        for (item in medicalFormData) {
            itemsForInMedicalFormAdapterList.add(
                GroupTitleItem(item.name)
            )

            if (item.contentElements.isNullOrEmpty()) {
                itemsForInMedicalFormAdapterList.removeAt(itemsForInMedicalFormAdapterList.size - 1)
                continue
            }

            for (contentElement in item.contentElements) {
                when (contentElement?.type) {
                    ContentElementType.CHECKBOX -> {
                        ++numberOfFormFields
                        itemsForInMedicalFormAdapterList.add(
                            CheckBoxItem(
                                contentElementDataMapper.mapToDto(
                                    contentElement
                                )
                            )
                        )
                    }
                    ContentElementType.TEXTBOX -> {
                        ++numberOfFormFields
                        itemsForInMedicalFormAdapterList.add(
                            TextBoxItem(
                                contentElementDataMapper.mapToDto(
                                    contentElement
                                )
                            )
                        )
                    }
                }
            }
        }

        itemsForInMedicalFormAdapterList.add(SaveButtonItem())

        this.itemsForInMedicalFormAdapterList.postValue(itemsForInMedicalFormAdapterList)
    }

    fun saveField(
        field: CustomerMedicalFormField
    ) {
        // create element
        val inputListElement =
            CustomerMedicalFormField(
                contentElementId = field.contentElementId,
                fieldValue = field.fieldValue
            )

        //add element to list
        contentElementsFieldList.add(inputListElement)

        if (contentElementsFieldList.size == numberOfFormFields) {

            val input = CustomerMedicalForm(
                Input.fromNullable(
                    contentElementsFieldList
                )
            )

            notifyActivityForSuspendFunctionScope.value = input
        }
    }

    fun clearContentElementsList() {
        // clear elements from previous state when save was called but one required field was  null/empty
        // or form was form was saved
        contentElementsFieldList.clear()
    }

    suspend fun saveMedicalFormData(input: CustomerMedicalForm) {

        val apiResponse = apiManagerInterface.saveMedicalFormAsync(input).await()

        clearContentElementsList()

        if (apiResponse.data?.saveCustomerMedicalForm?.errorMessage != null) {
            showErrorBanner.postValue(apiResponse.data?.saveCustomerMedicalForm?.errorMessage)
            return
        }

        showSuccessBanner.postValue(true)
    }

}

