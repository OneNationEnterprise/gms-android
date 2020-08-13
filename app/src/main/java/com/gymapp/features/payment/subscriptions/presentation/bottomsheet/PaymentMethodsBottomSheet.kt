package com.gymapp.features.payment.subscriptions.presentation.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.features.payment.subscriptions.presentation.adapter.AvailablePaymentMethodsListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gymapp.R
import com.gymapp.features.payment.subscriptions.presentation.adapter.AvailablePaymentMethodsAdapter
import com.gymapp.features.payment.subscriptions.presentation.adapter.PaymentMethodInterface
import com.gymapp.features.payment.subscriptions.presentation.adapter.item.ChoosePaymentMethodItem
import kotlinx.android.synthetic.main.modalbottomsheet_payment_methods.*

class PaymentMethodsBottomSheet(
    var paymentMethods: MutableList<PaymentMethodInterface>,
    val listener: BottomSheetSelectedPaymentMethodsListener
) : BottomSheetDialogFragment(),
    AvailablePaymentMethodsListener {

    private var selectedPaymentMethodId: PaymentMethodInterface = ChoosePaymentMethodItem()
    private lateinit var availablePaymentMethodsAdapter: AvailablePaymentMethodsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.modalbottomsheet_payment_methods, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        availablePaymentMethodsAdapter = AvailablePaymentMethodsAdapter(paymentMethods, this)

        paymentMethodsRv.layoutManager = linearLayoutManager
        paymentMethodsRv.adapter = availablePaymentMethodsAdapter

        closePaymentMethodIv.setOnClickListener {
            this.dismiss()
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            ) as? FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        return dialog
    }

    companion object {
        const val TAG = "PaymentMethodsBottomSheet"
    }

    override fun hasSelectedPaymentMethod(paymentMethodInterface: PaymentMethodInterface) {
        selectedPaymentMethodId = paymentMethodInterface
        listener.hasSelectedPaymentMethod(selectedPaymentMethodId)
        this.dismiss()
    }

    override fun hasSelectedAddNewCreditCard() {
        listener.hasSelectedAddCreditCard()
        this.dismiss()
    }

    override fun notifyDataSetChanged(paymentMethod: PaymentMethodInterface) {

        selectedPaymentMethodId = paymentMethod
        availablePaymentMethodsAdapter.updateSelectedPaymentMethodsList(selectedPaymentMethodId)
    }
}