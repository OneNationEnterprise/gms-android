package com.gymapp.helper.ui

import android.content.Context
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.TextView
import com.gymapp.R
import com.shasin.notificationbanner.Banner

object InAppBannerNotification {

    fun showErrorNotification(containerView: View, context: Context?, message: String?) {
        val errorMessage = if (isEmpty(message)) "Error" else message
        Banner.make(containerView, context, Banner.TOP, R.layout.inapp_notification_banner_error)
        Banner.getInstance().bannerView.findViewById<TextView>(R.id.notificationMessageTextView).text = errorMessage
        Banner.getInstance().setCustomAnimationStyle(R.style.NotificationAnimationTop)
        Banner.getInstance().duration = 2000
        Banner.getInstance().show()
    }

    fun showSuccessNotification(containerView: View, context: Context?, message: String?) {
        val errorMessage = if (isEmpty(message)) "Success" else message
        Banner.make(containerView, context, Banner.TOP, R.layout.inapp_notification_banner_success)
        Banner.getInstance().bannerView.findViewById<TextView>(R.id.notificationMessageTextView).text = errorMessage
        Banner.getInstance().setCustomAnimationStyle(R.style.NotificationAnimationTop)
        Banner.getInstance().duration = 2000
        Banner.getInstance().show()
    }
}