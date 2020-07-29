package com.gymapp.features.profile.medical.data.items

import com.apollographql.apollo.gym.GetMedicalFormQuery
import com.gymapp.features.profile.medical.data.MedicalFormListObject
import com.gymapp.helper.MedicalFormRecycleViewItemType

class TextBoxItem(val data: GetMedicalFormQuery.ContentElement) : MedicalFormListObject {
    override fun getType(): MedicalFormRecycleViewItemType {
        return MedicalFormRecycleViewItemType.TEXTBOX
    }
}
