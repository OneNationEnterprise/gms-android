package com.gymapp.features.gymdetail.presentation.adapter

import com.apollographql.apollo.gym.GymClassCategoriesQuery

interface CategorySelectedListener {

    fun onCategorySelected(category: GymClassCategoriesQuery.List)
}