package com.gymapp.features.store.data

import com.gymapp.helper.HomepageSectionType

interface HomepageSection {

    fun getType(): HomepageSectionType
    fun getHeaderTitle(): String
}