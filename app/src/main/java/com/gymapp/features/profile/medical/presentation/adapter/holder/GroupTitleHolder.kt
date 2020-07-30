package com.gymapp.features.profile.medical.presentation.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.features.profile.medical.data.MedicalFormListObject
import com.gymapp.features.profile.medical.data.items.GroupTitleItem
import kotlinx.android.synthetic.main.item_medical_form_group_title.view.*

class GroupTitleHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    fun bindView(medicalFormListObject: MedicalFormListObject) {
        val item = medicalFormListObject as GroupTitleItem

        itemView.nameValueTv.text = item.title
    }
}