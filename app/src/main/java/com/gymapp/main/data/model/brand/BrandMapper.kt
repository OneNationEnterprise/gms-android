package com.gymapp.main.data.model.brand

import com.apollographql.apollo.gym.fragment.BrandFields
import com.gymapp.base.data.BaseDataMapperInterface

class BrandMapper : BaseDataMapperInterface<BrandFields, Brand> {

    override fun mapToDto(input: BrandFields): Brand {
        return Brand(
            brandId = input.id,
            name = input.name,
            phone = input.phone,
            gymCount = input.gymCount,
            logo = input.logo,
            image = input.image
        )
    }

    override fun mapToDtoList(input: List<BrandFields?>?): List<Brand> {

        if (input.isNullOrEmpty()) return ArrayList()

        return input.map {
            mapToDto(it!!)
        }
    }
}