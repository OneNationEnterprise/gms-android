package com.gymapp.base.data

import com.apollographql.apollo.gym.CountriesQuery

interface BaseDataMapperInterface<I, O> {
    fun mapToDto(input: I): O
    fun mapToDtoList(input: List<I?>): List<O>
}