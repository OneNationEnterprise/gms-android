package com.gymapp.features.profile.addresses.presentation

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import com.gymapp.R
import com.gymapp.main.data.model.location.Address
import com.gymapp.main.data.model.user.DynamicAddressData
import kotlinx.android.synthetic.main.item_input_text.view.*
import render.animations.Attention
import render.animations.Render

class InputFieldDynamicView(context: Context, private val addressField: DynamicAddressData) : ConstraintLayout(context) {

    init {
        initialize()
    }

    private fun initialize() {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.item_input_text, this, true)

        textInputEditText.doAfterTextChanged {
            textInputLayout.isErrorEnabled = false
        }

        textInputLayout.setHint(addressField.name)
        textInputEditText.setText(addressField.value)
    }

    fun isRequired(): Boolean {
        return addressField.isRequired
    }

    fun getValue(): String {
        return textInputEditText.text.toString()
    }

    fun getFieldId(): String {
        return addressField.id
    }

    fun showError() {
        val render = Render(context)
        render.setAnimation(Attention().Bounce(textInputLayout))
        render.start()
        textInputLayout.error = context.getString(R.string.error_required_field)
    }

}