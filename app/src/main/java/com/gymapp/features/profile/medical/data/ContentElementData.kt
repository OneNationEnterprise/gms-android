package com.gymapp.features.profile.medical.data

import com.apollographql.apollo.gym.type.ContentElementType

data class ContentElementData(
    val id:String,
    val name:String?,
    val isRequired:Boolean?,
    val hint:String?,
    var value:String?,
    val type: ContentElementType
)