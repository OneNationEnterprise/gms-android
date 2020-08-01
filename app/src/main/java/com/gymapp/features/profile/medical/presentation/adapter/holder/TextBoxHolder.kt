package com.gymapp.features.profile.medical.presentation.adapter.holder

import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.gym.type.CustomerMedicalFormField
import com.gymapp.R
import com.gymapp.features.profile.medical.data.MedicalFormListObject
import com.gymapp.features.profile.medical.data.items.TextBoxItem
import com.gymapp.features.profile.medical.presentation.adapter.MedicalFormAdapter
import com.gymapp.features.profile.medical.presentation.adapter.MedicalFormItemListener
import kotlinx.android.synthetic.main.item_medical_form_textbox.view.*

class TextBoxHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    fun bindView(
        medicalFormListObject: MedicalFormListObject,
        medicalFormItemListener: MedicalFormItemListener,
        position: Int
    ) {
        val item = medicalFormListObject as TextBoxItem

        if (!item.data.value.isNullOrEmpty()) {
            itemView.contentElementInputEditText.setText(item.data.value)
        }
        itemView.contentElementInputLayout.hint = item.data.hint

        itemView.contentElementInputEditText.doAfterTextChanged {
            itemView.contentElementInputLayout.isErrorEnabled = false
            item.data.value = it.toString()
//            medicalFormItemListener.updateFieldValue(position, item)
        }

        // save field
        if (MedicalFormAdapter.saveField) {

            //check if data is mandatory and completed
            if ((item.data.isRequired != null && item.data.isRequired) && itemView.contentElementInputEditText.text.toString()
                    .isEmpty()
            ) {
                MedicalFormAdapter.saveField = false

                itemView.contentElementInputLayout.error =
                    itemView.context.getString(R.string.input_field_error_msg)

                medicalFormItemListener.dismissLoadingState()
                return
            }

            /** callback to [MedicalFormViewModel.kt]  to add field to saved list*/
//            medicalFormItemListener.saveField(
//                CustomerMedicalFormField(
//                    item.data.id,
//                    item.data.value ?: ""
//                )
//            )
        }

    }
}