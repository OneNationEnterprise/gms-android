package com.gymapp.features.payment.subscriptions.presentation.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import com.gymapp.features.payment.subscriptions.presentation.SelectedPaymentMethodListener
import com.gymapp.features.payment.subscriptions.presentation.adapter.AvailablePaymentMethodsListener
import com.gymapp.features.payment.subscriptions.presentation.adapter.PaymentMethodInterface
import com.gymapp.features.payment.subscriptions.presentation.adapter.item.SavedCardPaymentMethodItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_payment_method_saved_card.view.*

class SavedCreditCardPaymentHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bindView(listener: AvailablePaymentMethodsListener, paymentMethodInterface: PaymentMethodInterface, selectedPaymentMethodId: PaymentMethodInterface) {
        val paymentMethodDetails = paymentMethodInterface as SavedCardPaymentMethodItem


        itemView.cardTypeTv.text = paymentMethodDetails.paymentMethodDetails.fragments.paymentMethodFields.name

        if (!paymentMethodDetails.paymentMethodDetails.fragments.paymentMethodFields.imageUrl.isNullOrEmpty()) {
            Picasso.get().load(paymentMethodDetails.paymentMethodDetails.fragments.paymentMethodFields.imageUrl).into(itemView.myFatoorahPaymentIcon)
        }

        if (selectedPaymentMethodId == paymentMethodInterface) {
            itemView.myFatoorahPaymentCheckbox.setImageResource(R.drawable.ic_radiobtn_on)
        } else {
            itemView.myFatoorahPaymentCheckbox.setImageResource(R.drawable.ic_radiobutton_iconoff)
        }

        itemView.cardDetailsTv.text = paymentMethodDetails.paymentMethodDetails.fragments.paymentMethodFields.subText

        itemView.myFatoorahPaymentContainer.setOnClickListener {
            listener.hasSelectedPaymentMethod(paymentMethodInterface)
        }
    }
}


