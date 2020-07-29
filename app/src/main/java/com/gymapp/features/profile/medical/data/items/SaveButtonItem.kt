package com.gymapp.features.profile.medical.data.items

import com.gymapp.features.profile.medical.data.MedicalFormListObject
import com.gymapp.helper.MedicalFormRecycleViewItemType

class SaveButtonItem : MedicalFormListObject {
    override fun getType(): MedicalFormRecycleViewItemType {
        return MedicalFormRecycleViewItemType.SAVE_BUTTON
    }
}