package com.gymapp.features.profile.medical.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gymapp.R
import com.gymapp.features.gymdetail.presentation.adapter.StoreCategoryViewHolder
import com.gymapp.features.profile.medical.data.MedicalFormListObject
import com.gymapp.features.profile.medical.presentation.adapter.holder.CheckBoxHolder
import com.gymapp.features.profile.medical.presentation.adapter.holder.GroupTitleHolder
import com.gymapp.features.profile.medical.presentation.adapter.holder.SaveButtonHolder
import com.gymapp.features.profile.medical.presentation.adapter.holder.TextBoxHolder
import com.gymapp.helper.MedicalFormRecycleViewItemType

class MedicalFormAdapter(
    var contentElements: List<MedicalFormListObject>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            MedicalFormRecycleViewItemType.TEXTBOX.ordinal -> {
                TextBoxHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_medical_form_textbox, parent, false)
                )
            }
            MedicalFormRecycleViewItemType.CHECKBOX.ordinal -> {
                CheckBoxHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_medical_form_checkbox, parent, false)
                )
            }
            MedicalFormRecycleViewItemType.GROUP_TITLE.ordinal -> {
                GroupTitleHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_medical_form_group_title, parent, false)
                )
            }
            MedicalFormRecycleViewItemType.SAVE_BUTTON.ordinal -> {
                SaveButtonHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_medical_form_save_btn, parent, false)
                )
            }
            else -> {
                StoreCategoryViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_gym_detail_class_category, parent, false)
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return contentElements[position].getType().ordinal
    }

    override fun getItemCount(): Int {
        return contentElements.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (contentElements[position].getType()) {
            MedicalFormRecycleViewItemType.TEXTBOX -> {
                (holder as TextBoxHolder).bindView(contentElements[position])
            }
            MedicalFormRecycleViewItemType.CHECKBOX -> {
                (holder as CheckBoxHolder).bindView(contentElements[position])
            }
            MedicalFormRecycleViewItemType.GROUP_TITLE -> {
                (holder as GroupTitleHolder).bindView(contentElements[position])
            }
            MedicalFormRecycleViewItemType.SAVE_BUTTON -> {
                (holder as SaveButtonHolder).bindView(contentElements[position])
            }
        }
    }

    fun updateList(contentElements: List<MedicalFormListObject>) {
        this.contentElements = contentElements
        notifyDataSetChanged()
    }

}