package com.gymapp.features.profile.medical.presentation.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.widget.doAfterTextChanged
import com.gymapp.R
import com.gymapp.features.profile.medical.data.MedicalFormListObject
import com.gymapp.features.profile.medical.data.items.CheckBoxItem
import com.gymapp.features.profile.medical.data.items.GroupTitleItem
import com.gymapp.features.profile.medical.data.items.TextBoxItem
import com.gymapp.features.profile.medical.presentation.adapter.MedicalFormItemListener
import com.gymapp.helper.MedicalFormRecycleViewItemType
import kotlinx.android.synthetic.main.item_medical_form_checkbox.view.*
import kotlinx.android.synthetic.main.item_medical_form_group_title.view.*
import kotlinx.android.synthetic.main.item_medical_form_save_btn.view.*
import kotlinx.android.synthetic.main.item_medical_form_textbox.view.*

class MedicalFormCustomView(context: Context, item: MedicalFormListObject, medicalFormItemListener: MedicalFormItemListener) : LinearLayout(context) {

    var isRequired: Boolean = false
    lateinit var id: String
    var value: String? = null

    init {
        when (item.getType()) {
            MedicalFormRecycleViewItemType.TEXTBOX -> {
                val inflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.item_medical_form_textbox, this, true)

                initTextBoxView(item)
            }
            MedicalFormRecycleViewItemType.CHECKBOX -> {
                val inflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.item_medical_form_checkbox, this, true)

                initCheckBoxView(item)
            }
            MedicalFormRecycleViewItemType.GROUP_TITLE -> {
                val inflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.item_medical_form_group_title, this, true)

                initGroupTitleView(item)
            }
            MedicalFormRecycleViewItemType.SAVE_BUTTON -> {
                val inflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.item_medical_form_save_btn, this, true)

                initSaveButton(medicalFormItemListener)
            }
        }
    }

    private fun initTextBoxView(data: MedicalFormListObject) {
        val item = data as TextBoxItem

        if (!item.data.value.isNullOrEmpty()) {
            contentElementInputEditText.setText(item.data.value)
        }
        contentElementInputLayout.hint = item.data.hint

        isRequired = item.data.isRequired ?: false

        value = item.data.value ?: ""
        id = item.data.id

        contentElementInputEditText.doAfterTextChanged {
            contentElementInputLayout.isErrorEnabled = false
            value = it.toString()
        }
    }

    fun showTextBoxError() {
        contentElementInputLayout.error =
            context.getString(R.string.input_field_error_msg)
    }

    private fun initCheckBoxView(data: MedicalFormListObject) {
        val item = data as CheckBoxItem

        textValueTv.text = item.data.name

        if (item.data.value.isNullOrEmpty() || item.data.value.equals("false")) {
            value = "false"
            switchInput.isChecked = false
        } else {
            value = "true"
            switchInput.isChecked = true
        }

        id = item.data.id

        switchInput.setOnCheckedChangeListener { _, isChecked ->
            value = if (isChecked) {
                "true"
            } else {
                "false"
            }
        }
    }

    private fun initGroupTitleView(data: MedicalFormListObject) {
        val item = data as GroupTitleItem

        if (!item.title.isNullOrEmpty()) {
            nameValueTv.text = item.title
        }
    }

    private fun initSaveButton(medicalFormItemListener: MedicalFormItemListener) {
        saveBtn.setOnClickListener {
            medicalFormItemListener.onSaveClicked()
        }
    }

}