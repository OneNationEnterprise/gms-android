package com.gymapp.features.subscriptions.presentation.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import com.gymapp.main.data.model.subscription.Subscription
import kotlinx.android.synthetic.main.activity_subscription.*
import kotlinx.android.synthetic.main.item_subscription.view.*
import java.lang.IllegalArgumentException

class SubscriptionAdapter(
    private val subscriptionList: List<Subscription>,
    var selectedItemId: String,
    private val subscriptionSelectedListener: SubscriptionSelectedListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        return when (viewType) {
            1 -> {
                EmptyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.empty_view, parent, false)
                )
            }
            0 -> {
                SubscriptionViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_subscription, parent, false)
                )
            }
            else -> {
                EmptyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.empty_view, parent, false)
                )
            }
        }

    }

    override fun getItemCount(): Int {
        return subscriptionList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == subscriptionList.size) {
            return 1
        }
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position == subscriptionList.size) {
            (holder as EmptyViewHolder).bindView()
        } else {
            (holder as SubscriptionViewHolder).bindView(
                subscriptionList[position],
                selectedItemId == subscriptionList[position].id,
                subscriptionSelectedListener
            )
        }

    }

    fun updateSelectedItemId(id: String) {
        selectedItemId = id
        notifyDataSetChanged()
    }

}

class SubscriptionViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    fun bindView(
        subscription: Subscription,
        isItemSelected: Boolean,
        subscriptionSelectedListener: SubscriptionSelectedListener
    ) {

        itemView.selectorIv.isChecked = isItemSelected

        itemView.amountTv.text = subscription.amountLabel
        itemView.descriptionTv.text = subscription.description
        itemView.titleTv.text = subscription.name

        var colorCode = subscription.colorCode

        try {
            if (!colorCode.isNullOrEmpty()) {

                if (!colorCode.contains("#")) {
                    colorCode = "#$colorCode"
                }

                ImageViewCompat.setImageTintList(
                    itemView.startColorIv,
                    ColorStateList.valueOf(Color.parseColor(colorCode))
                )
                ImageViewCompat.setImageTintList(
                    itemView.overlayBackgroundIv,
                    ColorStateList.valueOf(Color.parseColor(colorCode))
                )

            }
        } catch (e: IllegalArgumentException) {
        }

        itemView.subscriptionContainer.setOnClickListener {
            subscriptionSelectedListener.onSubscriptionSelected(subscription)
        }

        itemView.selectorIv.setOnClickListener {
            subscriptionSelectedListener.onSubscriptionSelected(subscription)
        }


    }
}

class EmptyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    fun bindView(
    ) {

    }
}

