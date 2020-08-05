package com.gymapp.helper


enum class SubscriptionType {
    MEMBERSHIP,
    PASS
}


enum class MedicalFormRecycleViewItemType {
    TEXTBOX,
    GROUP_TITLE,
    CHECKBOX,
    SAVE_BUTTON
}

enum class HomepageSectionType {
    IMAGE,
    STORE,
    CATEGORY,
    PRODUCT
}

/**
 * enum class for menu items adapter [StoreCartActivity] class
 */
enum class STORE_CART_ITEMS {
    SECTION_HEADER,
    SELECTED_PRODUCT,
    SECTION_SUBTOTAL
}

enum class STORE_CART_PRODUCTS {
    BRAND_HEADER,
    PRODUCT_ROW,
    SUBTOTAL
}