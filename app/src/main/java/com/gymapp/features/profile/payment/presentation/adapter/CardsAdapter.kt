package com.gymapp.features.profile.payment.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.gym.GetCustomerCardTokensQuery
import com.checkout.android_sdk.Utils.CardUtils
import com.gymapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_card.view.*
import java.util.*

class CardsAdapter(
    var cards: MutableList<GetCustomerCardTokensQuery.GetCustomerCardToken?>,
    val cardClickListener: CardAdapterListener
) : RecyclerView.Adapter<CardsAdapter.CardsListViewHolder>() {

    private var showDeleteIcon = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsListViewHolder {

        return CardsListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: CardsListViewHolder, position: Int) {
        holder.bindView(showDeleteIcon, cardClickListener, cards[position])
    }

    fun showDeleteIcon() {
        showDeleteIcon = true
        notifyDataSetChanged()
    }

    fun hideDeleteIcon() {
        showDeleteIcon = false
        notifyDataSetChanged()
    }

    class CardsListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun bindView(
            showDeleteIcon: Boolean,
            cardClickListener: CardAdapterListener,
            card: GetCustomerCardTokensQuery.GetCustomerCardToken?
        ) {

            if (card == null) return


            when (card.scheme.toLowerCase(Locale.ROOT)) {
                "mastercard" -> {
                    Picasso.get().load(CardUtils.Cards.MASTERCARD.resourceId)
                        .into(itemView.cardIconIv)
                    itemView.cardNumber.text = "**** **** **** " + card.last4
                }
                "visa" -> {
                    Picasso.get().load(CardUtils.Cards.VISA.resourceId).into(itemView.cardIconIv)
                    itemView.cardNumber.text = "**** **** **** " + card.last4
                }
                "american express" -> {
                    Picasso.get().load(CardUtils.Cards.AMEX.resourceId).into(itemView.cardIconIv)
                    itemView.cardNumber.text = "**** ****** *" + card.last4
                }
                "maestro" -> {
                    Picasso.get().load(CardUtils.Cards.MAESTRO.resourceId).into(itemView.cardIconIv)
                }
                "discover" -> {
                    Picasso.get().load(CardUtils.Cards.DISCOVER.resourceId)
                        .into(itemView.cardIconIv)
                    itemView.cardNumber.text = "**** **** **** " + card.last4
                }
                "jcb" -> {
                    Picasso.get().load(CardUtils.Cards.JCB.resourceId).into(itemView.cardIconIv)
                    itemView.cardNumber.text = "**** **** **** " + card.last4
                }
                "diners club international" -> {
                    Picasso.get().load(CardUtils.Cards.DINERSCLUB.resourceId)
                        .into(itemView.cardIconIv)
                    itemView.cardNumber.text = "**** ****** " + card.last4
                }
            }

            val year = (card.expiryYear - 2000).toString()
            val month = if (card.expiryMonth < 10) {
                "0${card.expiryMonth}"
            } else {
                card.expiryMonth.toString()
            }

            itemView.cardBrand.text = card.scheme
            itemView.expiryDateValueTv.text = "${month}/${year}"


            if (showDeleteIcon) {
                itemView.deleteIconIv.visibility = View.VISIBLE
            } else {
                itemView.deleteIconIv.visibility = View.GONE
            }

            itemView.deleteIconIv.setOnClickListener {
                cardClickListener.onDeleteSelected(card.id, card.last4)
            }

        }
    }

}