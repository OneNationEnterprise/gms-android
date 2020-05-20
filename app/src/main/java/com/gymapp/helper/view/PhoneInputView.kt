package com.gymapp.helper.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnPreDraw
import com.bumptech.glide.Glide
import com.gymapp.R
import com.gymapp.helper.extensions.toPx
import kotlinx.android.synthetic.main.view_phone_input.view.*

class PhoneInputView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

     var viewContext: Context

    init {
        inflate(context, R.layout.view_phone_input, this)

        viewContext = context

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PhoneInputView)

        val hint = attributes.getString(R.styleable.PhoneInputView_hintText)

        if (!hint.isNullOrEmpty()) {
            inputPhoneNumberEditText.hint = hint
        }

        attributes.recycle()
    }

    fun setPhoneNumber(phoneNumber: String) {
        if (!phoneNumber.isNullOrEmpty()) {
            inputPhoneNumberEditText.setText(phoneNumber)
        }
    }

    fun getPhoneNumber(): String {
        return inputPhoneNumberEditText.text.toString()
    }

    fun setCountryDetails(dialCode: String?, flagUrl: String?) {

        if (!dialCode.isNullOrEmpty()) {
            phonePrefixTv.text = dialCode
        }

        if (!flagUrl.isNullOrEmpty()) {
            Glide.with(viewContext).load(flagUrl).into(flagIv)
        }

        phonePrefixHolder.doOnPreDraw {
            inputPhoneNumberEditText.setPadding((it.width.toPx() / resources.displayMetrics.density).toInt() + (2 * resources.getDimension(R.dimen.phone_input_prefix_paddingStart).toInt()),
                resources.getDimension(R.dimen.phone_input_edit_text_padding).toInt(),
                resources.getDimension(R.dimen.phone_input_edit_text_padding).toInt(),
                resources.getDimension(R.dimen.phone_input_edit_text_padding).toInt())
        }
    }


}