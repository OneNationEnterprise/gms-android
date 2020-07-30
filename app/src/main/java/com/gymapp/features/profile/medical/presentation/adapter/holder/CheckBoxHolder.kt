package com.gymapp.features.profile.medical.presentation.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.gym.type.CustomerMedicalFormField
import com.gymapp.features.profile.medical.data.MedicalFormListObject
import com.gymapp.features.profile.medical.data.items.CheckBoxItem
import com.gymapp.features.profile.medical.presentation.adapter.MedicalFormAdapter
import com.gymapp.features.profile.medical.presentation.adapter.MedicalFormItemListener
import kotlinx.android.synthetic.main.item_medical_form_checkbox.view.*

class CheckBoxHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    fun bindView(
        medicalFormListObject: MedicalFormListObject,
        medicalFormItemListener: MedicalFormItemListener,
        position: Int
    ) {
        val item = medicalFormListObject as CheckBoxItem

        itemView.textValueTv.text = item.data.name

        itemView.switchInput.isChecked = item.data.value == "true"

        itemView.switchInput.setOnCheckedChangeListener { _, isChecked ->

            item.data.value = if (isChecked) {
                "true"
            } else {
                "false"
            }

//            medicalFormItemListener.updateFieldValue(
//                position,
//                item
//            )
        }

        if (MedicalFormAdapter.saveField) {

            medicalFormItemListener.saveField(
                CustomerMedicalFormField(
                    item.data.id,
                    item.data.value ?: "false"
                )
            )
        }
    }
}