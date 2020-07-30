package com.gymapp.features.profile.medical.presentation.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.features.profile.medical.data.MedicalFormListObject
import com.gymapp.features.profile.medical.data.items.TextBoxItem
import kotlinx.android.synthetic.main.item_medical_form_textbox.view.*

class TextBoxHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    fun bindView(medicalFormListObject: MedicalFormListObject) {

        val item = medicalFormListObject as TextBoxItem

        if (!item.data.data?.value.isNullOrEmpty()) {
            itemView.contentElementInputEditText.setText(item.data.data?.value)
        }
        itemView.contentElementInputLayout.hint = item.data.data?.placeHolder
    }
}