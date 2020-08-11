package com.gymapp.features.profile.payment.presentation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.apollographql.apollo.gym.CustomerCardTokenSaveMutation
import com.checkout.android_sdk.CheckoutAPIClient
import com.checkout.android_sdk.Utils.CardUtils
import com.checkout.android_sdk.Utils.Environment
import com.google.gson.Gson
import com.gymapp.BuildConfig
import com.gymapp.R
import com.gymapp.base.presentation.BaseActivity
import com.gymapp.features.profile.payment.domain.SaveCardViewModel
import com.gymapp.helper.Constants
import com.gymapp.helper.ui.InAppBannerNotification
import kotlinx.android.synthetic.main.activity_save_card.*
import kotlinx.android.synthetic.main.item_save_card_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.act
import org.jetbrains.annotations.NotNull
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class SaveCardActivity : BaseActivity(R.layout.activity_save_card), CardViewInterface {

    lateinit var saveCardViewModel: SaveCardViewModel
    lateinit var activity: SaveCardActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        saveCardViewModel.viewInterface = this
        activity = this

        setTitle(getString(R.string.add_credit_card))

        addTextWatchers()

        saveCreditCard.setOnClickListener {
            checkInputDataAndTokenizeCard()
        }

        GlobalScope.launch {
            saveCardViewModel.initCheckoutApi()
        }

    }

    override fun setupViewModel() {
        saveCardViewModel = getViewModel()
    }

    override fun bindViewModelObservers() {

    }

    private fun addTextWatchers() {

        cardNumberEt.addTextChangedListener(
            object : TextWatcher {

                var hasBeenFormatted = false

                override fun afterTextChanged(s: Editable?) {
                    inputCardNumber.isErrorEnabled = false
                    cardTypeIconIv.visibility = View.VISIBLE

                    if (s == null || s.isEmpty()) return

                    if (hasBeenFormatted) {
                        hasBeenFormatted = false
                        return
                    }

                    val formattedIbanInput = StringBuilder()

                    formattedIbanInput.append(CardUtils.getFormattedCardNumber(s.toString()))
                    val type = CardUtils.getType(s.toString().replace(" ", ""))

                    when (type) {
                        CardUtils.Cards.MASTERCARD -> {
                            cardTypeIconIv.setImageResource(CardUtils.Cards.MASTERCARD.resourceId)
                            if (formattedIbanInput.length == 19) {
                                expiryDateEt.requestFocus()
                            }
                        }
                        CardUtils.Cards.VISA -> {
                            cardTypeIconIv.setImageResource(CardUtils.Cards.VISA.resourceId)
                            if (formattedIbanInput.length == 19) {
                                expiryDateEt.requestFocus()
                            }
                        }
                        CardUtils.Cards.AMEX -> {
                            cardTypeIconIv.setImageResource(CardUtils.Cards.AMEX.resourceId)
                            if (formattedIbanInput.length == 17) {
                                expiryDateEt.requestFocus()
                            }
                        }
                        CardUtils.Cards.DISCOVER -> {
                            cardTypeIconIv.setImageResource(CardUtils.Cards.DISCOVER.resourceId)
                        }
                        CardUtils.Cards.JCB -> {
                            cardTypeIconIv.setImageResource(CardUtils.Cards.JCB.resourceId)
                        }
                        CardUtils.Cards.DINERSCLUB -> {
                            cardTypeIconIv.setImageResource(CardUtils.Cards.DINERSCLUB.resourceId)
                        }
                        CardUtils.Cards.MAESTRO -> {
                            cardTypeIconIv.setImageResource(CardUtils.Cards.MAESTRO.resourceId)
                        }
                        else -> {
                            cardTypeIconIv.setImageDrawable(null)
                        }
                    }

                    hasBeenFormatted = true
                    cardNumberEt.setText(formattedIbanInput)
                    cardNumberEt.setSelection(formattedIbanInput.length)
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            })

        expiryDateEt.addTextChangedListener(
            object : TextWatcher {

                private var hasBeenFormatted = false
                private var textLengthBeforeChange = 0

                override fun afterTextChanged(s: Editable?) {

                    inputExpiryDate.isErrorEnabled = false

                    if (hasBeenFormatted || s == null || textLengthBeforeChange > s.length) {
                        hasBeenFormatted = false
                        return
                    }

                    when (s.length) {
                        1 -> {
                            if (s[0] != '/' && Integer.parseInt(s[0].toString()) > 1) {
                                expiryDateEt.setText("0${s[0]}/")
                                expiryDateEt.setSelection(3)
                                hasBeenFormatted = true
                            }
                        }
                        2 -> {
                            if (!s.contains('/')) {
                                expiryDateEt.setText("${s[0]}${s[1]}/")
                                expiryDateEt.setSelection(3)
                                hasBeenFormatted = true
                            }

                        }
                        3 -> {
                        }
                        4 -> {
                        }
                        5 -> {
                            val formatter = SimpleDateFormat("MM/yy", Locale.ENGLISH)
                            try {
                                formatter.parse(s.toString())

                                CVVEt.requestFocus()

                            } catch (e: ParseException) {
                                inputExpiryDate.error = getString(R.string.card_date_error)
                            }
                        }
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    textLengthBeforeChange = s?.length ?: 0
                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            }
        )

        CVVEt.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    inputCVV.isErrorEnabled = false
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            }
        )
    }


    private fun checkInputDataAndTokenizeCard() {

        val checkoutEnvironment = if (BuildConfig.DEBUG) {
            Environment.SANDBOX
        } else {
            Environment.LIVE
        }
        val mCheckoutAPIClient = CheckoutAPIClient(
            this,  // context
            "TODO _ SET KEY",  // your public key
            checkoutEnvironment // the environment
        )
        mCheckoutAPIClient.setTokenListener(saveCardViewModel) // pass the callback

        val cardNumberFormatted = cardNumberEt.text.toString()
        val expiryDate = expiryDateEt.text.toString()
        val cvv = CVVEt.text.toString()

        val cardNumber = cardNumberFormatted.replace(" ", "")

        if (!CardUtils.isValidCard(cardNumber)) {
            setInvalidCardNumber()
            return
        }

        if (expiryDate.length != 5 || !CardUtils.isValidDate(
                expiryDate.substring(0, 2),
                (expiryDate.substring(3, 5).toInt() + 2000).toString()
            )
        ) {
            setInvalidDate()
            return
        }

        if (!CardUtils.isValidCvv(cvv, CardUtils.getType(cardNumber))) {
            setInvalidCVV()
            return
        }

        showLoading()
        saveCardViewModel.tokenizeCard(mCheckoutAPIClient, cardNumberFormatted, expiryDate, cvv)
    }

    override fun setInvalidCardNumber() {
        cardTypeIconIv.visibility = View.INVISIBLE
        cardNumberEt.error = getString(R.string.invalid_card_number)
    }

    override fun setInvalidCVV() {
        CVVEt.error = getString(R.string.invalid_cvv)
    }

    override fun setInvalidNameOnCard() {

    }

    override fun setInvalidDate() {
        expiryDateEt.error = getString(R.string.card_date_error)
    }


    override fun showLoading() {
        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        CoroutineScope(Dispatchers.Main).launch {
            progressBar.visibility = View.GONE
        }
    }

    override fun showErrorMessage(message: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            hideLoading()
            InAppBannerNotification.showErrorNotification(
                saveCardLayout,
                activity,
                message
            )
        }
    }

    override fun onCardSavedSuccess(data: CustomerCardTokenSaveMutation.CustomerCardToken) {
        CoroutineScope(Dispatchers.Main).launch {
            val intent = Intent()
            val args = Bundle()
            args.putString(Constants.customerSavedCardData, Gson().toJson(data))
            intent.putExtra(Constants.arguments, args)

            setResult(RESULT_OK, intent)
            finish()
        }
    }


    override fun initCheckoutApi(publicKey: String) {
        val checkoutEnvironment = if (BuildConfig.DEBUG) {
            Environment.SANDBOX
        } else {
            Environment.LIVE
        }
        val mCheckoutAPIClient = CheckoutAPIClient(
            this,  // context
            publicKey,  // your public key
            checkoutEnvironment // the environment
        )
        mCheckoutAPIClient.setTokenListener(saveCardViewModel) // pass the callback

        setSaveCardClickListener(mCheckoutAPIClient)
    }

    private fun setSaveCardClickListener(mCheckoutAPIClient: CheckoutAPIClient) {
        saveCreditCard.setOnClickListener {

            saveCardViewModel.tokenizeCard(
                mCheckoutAPIClient,
                cardNumberEt.text.toString(),
                expiryDateEt.text.toString(),
                CVVEt.text.toString()
            )
        }
    }


}