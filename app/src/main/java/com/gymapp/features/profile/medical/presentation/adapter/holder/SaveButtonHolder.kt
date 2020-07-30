package com.gymapp.features.profile.medical.presentation.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.features.profile.medical.data.MedicalFormListObject
import com.gymapp.features.profile.medical.presentation.adapter.MedicalFormItemListener
import kotlinx.android.synthetic.main.item_medical_form_save_btn.view.*

class SaveButtonHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bindView(medicalFormItemListener: MedicalFormItemListener) {

        itemView.saveBtn.setOnClickListener {
            medicalFormItemListener.notifyAdapterToSaveFields()
        }
    }
}