package com.gymapp.features.profile.medical.presentation.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.features.profile.medical.data.MedicalFormListObject
import com.gymapp.features.profile.medical.data.items.CheckBoxItem
import kotlinx.android.synthetic.main.item_medical_form_checkbox.view.*

class CheckBoxHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    fun bindView(medicalFormListObject: MedicalFormListObject) {
        val item = medicalFormListObject as CheckBoxItem

        itemView.textValueTv.text = item.data.data?.name
    }
}