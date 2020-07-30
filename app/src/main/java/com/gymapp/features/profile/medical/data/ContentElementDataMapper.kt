package com.gymapp.features.profile.medical.data

import com.apollographql.apollo.gym.GetMedicalFormQuery
import com.gymapp.base.data.BaseDataMapperInterface

class ContentElementDataMapper :
    BaseDataMapperInterface<GetMedicalFormQuery.ContentElement, ContentElementData> {
    override fun mapToDto(input: GetMedicalFormQuery.ContentElement): ContentElementData {
        return ContentElementData(
            id = input.id,
            name = input.data?.name,
            isRequired = input.data?.required,
            hint = input.data?.placeHolder,
            value = input.data?.value,
            type = input.type
        )
    }

    override fun mapToDtoList(input: List<GetMedicalFormQuery.ContentElement?>): List<ContentElementData> {
        return input.map {
            mapToDto(it!!)
        }
    }
}