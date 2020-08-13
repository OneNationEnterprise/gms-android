package com.gymapp.features.payment.subscriptions.domain

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.gym.CustomerCardTokenSaveMutation
import com.apollographql.apollo.gym.GetPaymentMethodsQuery
import com.apollographql.apollo.gym.type.*
import com.cofedistrict.ui.features.payment.adapter.paymentmethods.item.AddNewCardItem
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.payment.subscriptions.presentation.adapter.PaymentMethodInterface
import com.gymapp.features.payment.subscriptions.presentation.adapter.item.SavedCardPaymentMethodItem
import com.gymapp.features.payment.subscriptions.presentation.bottomsheet.BottomSheetSelectedPaymentMethodsListener
import com.gymapp.main.network.ApiManagerInterface

class PaymentViewModel(val apiManagerInterface: ApiManagerInterface) : BaseViewModel(),
    BottomSheetSelectedPaymentMethodsListener {

    val error = MutableLiveData<String>()
    val invoice = MutableLiveData<List<Invoice>>()
    val paymentMethods = MutableLiveData<List<PaymentMethodInterface>>()
    val selectedPaymentMethod = MutableLiveData<PaymentMethodInterface>()
    val openAddCardActivity = MutableLiveData<Boolean>()

    suspend fun getPassesInvoice(id: String) {

        val apiResponse = apiManagerInterface.getPassInvoiceAsync(PassInvoiceInput(id)).await()

        if (apiResponse.errors != null && apiResponse.errors!!.isNotEmpty()) {
            error.postValue(apiResponse.errors!![0].message)
            return
        }

        if ((apiResponse.data == null || apiResponse.data?.passInvoice?.components == null)) {
            error.postValue("Error on invoice computing")
            return
        }

        val invoice = ArrayList<Invoice>()
        for (component in apiResponse.data!!.passInvoice.components!!) {
            invoice.add(
                Invoice(
                    type = component.type,
                    value = component.value.toString(),
                    label = component.label
                )
            )
        }

        this.invoice.postValue(invoice)
        getPaymentMethods()
    }

    suspend fun getMemberInvoice(id: String) {

        val apiResponse =
            apiManagerInterface.getMemberInvoiceAsync(MembershipInvoiceInput(id)).await()

        if (apiResponse.errors != null && apiResponse.errors!!.isNotEmpty()) {
            error.postValue(apiResponse.errors!![0].message)
            return
        }

        if ((apiResponse.data == null || apiResponse.data?.membershipInvoice?.components == null)) {
            error.postValue("Error on invoice computing")
            return
        }

        val invoice = ArrayList<Invoice>()
        for (component in apiResponse.data!!.membershipInvoice!!.components!!) {
            invoice.add(
                Invoice(
                    type = component.type,
                    value = component.value.toString(),
                    label = component.label
                )
            )
        }

        this.invoice.postValue(invoice)
        getPaymentMethods()
    }

    suspend fun getGymClassInvoice(id: String) {

        val apiResponse =
            apiManagerInterface.getGymCLassInvoiceAsync(GymClassInvoiceInput(id)).await()

        if (apiResponse.errors != null && apiResponse.errors!!.isNotEmpty()) {
            error.postValue(apiResponse.errors!![0].message)
            return
        }

        if ((apiResponse.data == null || apiResponse.data?.gymClassInvoice?.components == null)) {
            error.postValue("Error on invoice computing")
            return
        }

        val invoice = ArrayList<Invoice>()
        for (component in apiResponse.data!!.gymClassInvoice!!.components!!) {
            invoice.add(
                Invoice(
                    type = component.type,
                    value = component.value.toString(),
                    label = component.label
                )
            )
        }

        this.invoice.postValue(invoice)

        getPaymentMethods()
    }

    private suspend fun getPaymentMethods() {
        val apiResponse = apiManagerInterface.getPaymentMethodsAsync("AE").await()

        if (apiResponse.errors != null && apiResponse.errors!!.isNotEmpty()
            ||
            (apiResponse.data == null || apiResponse.data?.getPaymentMethods == null)
        ) {
            error.postValue(apiResponse.errors!![0].message)
            return
        }

        val pmTempList = ArrayList<PaymentMethodInterface>()

        for (paymentMethod in apiResponse.data?.getPaymentMethods!!) {
            when (paymentMethod?.fragments?.paymentMethodFields?.paymentScheme) {
                PaymentScheme.SAVED_CARD -> {
                    pmTempList.add(SavedCardPaymentMethodItem(paymentMethod))
                }
                PaymentScheme.ADD_CARD -> {
                    pmTempList.add(AddNewCardItem())
                }
            }
        }

        paymentMethods.postValue(pmTempList)
    }

    data class Invoice(
        val type: InvoiceComponentType,
        val label: String?,
        val value: String
    )

    override fun hasSelectedAddCreditCard() {
        openAddCardActivity.value = true
    }

    override fun hasSelectedPaymentMethod(selectedPM: PaymentMethodInterface) {
        selectedPaymentMethod.postValue(selectedPM)
    }

    suspend fun cacheAddedCreditCardAndRefetchPaymentMethods(cardData: CustomerCardTokenSaveMutation.CustomerCardToken) {

        val apiResponse = apiManagerInterface.getPaymentMethodsAsync("AE").await()

        if (apiResponse.errors != null && apiResponse.errors!!.isNotEmpty()
            ||
            (apiResponse.data == null || apiResponse.data?.getPaymentMethods == null)
        ) {
            error.postValue(apiResponse.errors!![0].message)
            return
        }

        val pmTempList = ArrayList<PaymentMethodInterface>()

        for (paymentMethod in apiResponse.data?.getPaymentMethods!!) {
            when (paymentMethod?.fragments?.paymentMethodFields?.paymentScheme) {
                PaymentScheme.SAVED_CARD -> {
                    pmTempList.add(SavedCardPaymentMethodItem(paymentMethod))
                }
                PaymentScheme.ADD_CARD -> {
                    pmTempList.add(AddNewCardItem())
                }
            }

            if (paymentMethod?.fragments?.paymentMethodFields?.sourceId
                == cardData.fragments.customerCardTokenFields.id
            ) {
                hasSelectedPaymentMethod(SavedCardPaymentMethodItem(paymentMethod!!))
            }
        }

        paymentMethods.postValue(pmTempList)
    }
}