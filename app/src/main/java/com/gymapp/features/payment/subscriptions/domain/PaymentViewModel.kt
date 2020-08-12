package com.gymapp.features.payment.subscriptions.domain

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.gym.GetPaymentMethodsQuery
import com.apollographql.apollo.gym.type.GymClassInvoiceInput
import com.apollographql.apollo.gym.type.InvoiceComponentType
import com.apollographql.apollo.gym.type.MembershipInvoiceInput
import com.apollographql.apollo.gym.type.PassInvoiceInput
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.main.network.ApiManagerInterface

class PaymentViewModel(val apiManagerInterface: ApiManagerInterface) : BaseViewModel() {

    val error = MutableLiveData<String>()
    val invoice = MutableLiveData<List<Invoice>>()
    val paymentMethods = MutableLiveData<List<GetPaymentMethodsQuery.GetPaymentMethod?>>()

    suspend fun getPassesInvoice(id: String) {

        val apiResponse = apiManagerInterface.getPassInvoiceAsync(PassInvoiceInput(id)).await()

        if (apiResponse.errors != null && apiResponse.errors!!.isNotEmpty()
            ||
            (apiResponse.data == null || apiResponse.data?.passInvoice?.components == null)
        ) {
            error.postValue(apiResponse.errors!![0].message)
            return
        }

        val invoice = ArrayList<Invoice>()
        for (component in apiResponse.data!!.passInvoice!!.components!!) {
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

        if (apiResponse.errors != null && apiResponse.errors!!.isNotEmpty()
            ||
            (apiResponse.data == null || apiResponse.data?.membershipInvoice?.components == null)
        ) {
            error.postValue(apiResponse.errors!![0].message)
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

        if (apiResponse.errors != null && apiResponse.errors!!.isNotEmpty()
            ||
            (apiResponse.data == null || apiResponse.data?.gymClassInvoice?.components == null)
        ) {
            error.postValue(apiResponse.errors!![0].message)
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

        paymentMethods.postValue(apiResponse.data?.getPaymentMethods)
    }


    data class Invoice(
        val type: InvoiceComponentType,
        val label: String?,
        val value: String
    )
}