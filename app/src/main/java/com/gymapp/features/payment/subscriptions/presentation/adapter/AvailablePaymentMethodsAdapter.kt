package com.gymapp.features.payment.subscriptions.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import com.gymapp.features.payment.subscriptions.presentation.adapter.holder.AddNewCreditCardHolder
import com.gymapp.features.payment.subscriptions.presentation.adapter.holder.SavedCreditCardPaymentHolder
import com.gymapp.features.payment.subscriptions.presentation.adapter.item.ChoosePaymentMethodItem
import com.gymapp.helper.PAYMENT_METHOD_TYPE

class AvailablePaymentMethodsAdapter(var paymentMethods: MutableList<PaymentMethodInterface>,
                                     val listener: AvailablePaymentMethodsListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectedPaymentMethodId: PaymentMethodInterface = ChoosePaymentMethodItem()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
           PAYMENT_METHOD_TYPE.SAVED_CARD.ordinal -> SavedCreditCardPaymentHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_payment_method_saved_card, parent, false))
           PAYMENT_METHOD_TYPE.ADD_CREDIT_CARD.ordinal -> AddNewCreditCardHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_add_credit_card, parent, false))
            else -> {
                SavedCreditCardPaymentHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_payment_method_saved_card, parent, false))
            }
        }
    }

    fun updateSelectedPaymentMethodsList(selectedPaymentMethodId: PaymentMethodInterface) {
        this.selectedPaymentMethodId= selectedPaymentMethodId
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return paymentMethods.size
    }

    override fun getItemViewType(position: Int): Int {
        return paymentMethods[position].getType()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {

          PAYMENT_METHOD_TYPE.SAVED_CARD.ordinal -> {
                val viewHolder = holder as SavedCreditCardPaymentHolder
                viewHolder.bindView(listener, paymentMethods[position], selectedPaymentMethodId)
            }
           PAYMENT_METHOD_TYPE.ADD_CREDIT_CARD.ordinal -> {
                val viewHolder = holder as AddNewCreditCardHolder
                viewHolder.bindView(listener)
            }
            else -> {
            }
        }
    }
}