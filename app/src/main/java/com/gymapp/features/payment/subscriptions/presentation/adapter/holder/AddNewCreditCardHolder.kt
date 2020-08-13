package com.gymapp.features.payment.subscriptions.presentation.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.features.payment.subscriptions.presentation.SelectedPaymentMethodListener
import com.gymapp.features.payment.subscriptions.presentation.adapter.AvailablePaymentMethodsListener
import kotlinx.android.synthetic.main.item_add_credit_card.view.*

class AddNewCreditCardHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bindView(listener: AvailablePaymentMethodsListener) {

        itemView.addCreditCardContainer.setOnClickListener {
            listener.hasSelectedAddNewCreditCard()
        }
    }
}