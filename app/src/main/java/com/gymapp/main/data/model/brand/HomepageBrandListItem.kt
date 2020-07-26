package com.gymapp.main.data.model.brand

data class HomepageBrandListItem(
    val brand: Brand,
    val gymImage: String,
    var gymCount: Int = 1
)