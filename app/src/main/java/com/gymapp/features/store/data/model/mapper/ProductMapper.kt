package com.gymapp.features.store.data.model.mapper

import com.apollographql.apollo.gym.StoreHomeQuery
import com.apollographql.apollo.gym.fragment.StoreProductFields
import com.gymapp.base.data.BaseDataMapperInterface
import com.gymapp.features.store.data.model.Inventory
import com.gymapp.features.store.data.model.Product
import com.gymapp.features.store.data.model.ProductBrand

class ProductMapper : BaseDataMapperInterface<StoreProductFields, Product> {

    override fun mapToDto(fields: StoreProductFields): Product {

        return Product(
            id = fields.id,
            name = fields.name,
            status = fields.status,
            description = fields.description,
            listPrice = fields.listPrice.toString().toDouble(),
            salePrice = fields.salePrice.toString().toDouble(),
            warranty = fields.warranty,
            warrantyDescription = fields.warrantyDescription,
            returnPolicy = fields.returnPolicy,
            returnPolicyDescription = fields.returnPolicyDescription,
            brand = productBrandMapper(fields.brand),
            express = fields.express,
            images = fields.images,
            categoryName = fields.category?.name,
            inventory = inventoryMapper(fields.inventory)
        )
    }

    override fun mapToDtoList(input: List<StoreProductFields?>?): List<Product> {

        if (input.isNullOrEmpty()) return ArrayList()

        return input.map {
            mapToDto(it!!)
        }
    }

    fun mapToDtoCustomList(input: List<StoreHomeQuery.BestSeller?>?): List<Product> {

        if (input.isNullOrEmpty()) return ArrayList()

        return input.map {
            mapToDto(it!!.fragments.storeProductFields)
        }
    }

    fun productBrandMapper(brand: StoreProductFields.Brand?): ProductBrand? {
        return if (brand != null) {
            ProductBrand(brand.id, brand.name)
        } else {
            null
        }
    }

    fun inventoryMapper(inventory: List<StoreProductFields.Inventory>?): List<Inventory?>? {
        return if (inventory != null) {

            val inventory2 = ArrayList<Inventory>()
            for (inv in inventory) {
                inventory2.add(
                    Inventory(
                        inv.gymId,
                        inv.quantity
                    )
                )
            }

            return inventory2
        } else {
            null
        }
    }
}