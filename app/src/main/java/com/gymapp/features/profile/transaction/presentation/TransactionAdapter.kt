package com.gymapp.features.profile.transaction.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.gym.GetCustomerCardTokensQuery
import com.apollographql.apollo.gym.TransactionsQuery
import com.checkout.android_sdk.Utils.CardUtils
import com.gymapp.R

class TransactionAdapter(
    var transactions: List<TransactionsQuery.List?>
) : RecyclerView.Adapter<TransactionAdapter.CardsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsListViewHolder {

        return CardsListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_transaction, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(holder: CardsListViewHolder, position: Int) {
        holder.bindView(transactions[position])
    }

    class CardsListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        fun bindView(
            transaction: TransactionsQuery.List?
        ) {


        }
    }

}