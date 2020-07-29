package com.gymapp.features.profile.medical.data.items

import com.gymapp.features.profile.medical.data.MedicalFormListObject
import com.gymapp.helper.MedicalFormRecycleViewItemType

class GroupTitleItem (val title : String) : MedicalFormListObject {
    override fun getType(): MedicalFormRecycleViewItemType {
        return MedicalFormRecycleViewItemType.GROUP_TITLE
    }
}