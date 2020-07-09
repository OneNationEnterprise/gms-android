package com.gymapp.main.data.model.brand

import com.apollographql.apollo.gym.CountriesQuery
import com.apollographql.apollo.gym.fragment.BrandFields
import com.gymapp.base.data.BaseDataMapperInterface

class BrandMapper : BaseDataMapperInterface<BrandFields, Brand> {

    override fun mapToDto(input: BrandFields): Brand {
        return Brand(
            id = input.id,
            name = input.name,
            phone = input.phone,
            gymCount = input.gymCount
        )
    }

    override fun mapToDtoList(input: List<CountriesQuery.List>): List<Brand> {
        return input.map {
            mapToDto(it)
        }
    }
}