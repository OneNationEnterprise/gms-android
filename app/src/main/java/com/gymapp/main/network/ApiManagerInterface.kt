package com.gymapp.main.network

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.gym.*
import com.apollographql.apollo.gym.type.*
import kotlinx.coroutines.Deferred

interface ApiManagerInterface {

    suspend fun getCountriesAsync(): Deferred<Response<CountriesQuery.Data>>

    suspend fun registerUserAsync(input: RegisterCustomerInput): Deferred<Response<RegisterUserMutation.Data>>

    suspend fun getUserDetailsByEmailAsync(email: String): Deferred<Response<CustomerByEmailQuery.Data>>

    suspend fun getGymsInRadiusAsync(input: GymsInRadiusFilter): Deferred<Response<GymsInRadiusQuery.Data>>

    suspend fun getGymDetailAsync(id: String): Deferred<Response<GymQuery.Data>>

    suspend fun getGymCategoriesAsync(input: Input<GymClassCategoryFilter>): Deferred<Response<GymClassCategoriesQuery.Data>>

    suspend fun getMembershipAsync(input: Input<MembershipsFilter>): Deferred<Response<MembershipsQuery.Data>>

    suspend fun getPassesAsync(input: Input<PassesFilter>): Deferred<Response<PassesQuery.Data>>

    suspend fun getClassesAsync(input: Input<GymClassesFilter>): Deferred<Response<ClassesQuery.Data>>

    suspend fun getMedicalFormAsync(): Deferred<Response<GetMedicalFormQuery.Data>>

    suspend fun saveMedicalFormAsync(input: CustomerMedicalForm): Deferred<Response<SaveMedicalFormMutation.Data>>

    suspend fun getStoreHomeAsync(input: StoreHomeInput?): Deferred<Response<StoreHomeQuery.Data>>

    suspend fun getProductsAsync(
        input: ProductsFilter,
        pagingInput: PaginatorInput
    ): Deferred<Response<ProductsQuery.Data>>

    suspend fun saveCustomerAsync(input: SaveCustomerInput): Deferred<Response<SaveCustomerMutation.Data>>

    suspend fun saveCustomerAddressAsync(input: SaveCustomerAddressInput): Deferred<Response<SaveAddressMutation.Data>>

    suspend fun getCheckoutComConfigAsync(): Deferred<Response<GetCheckoutComConfigQuery.Data>>

    suspend fun customerCardTokenSaveAsync(cardTokenInput: CustomerCardTokenInput): Deferred<Response<CustomerCardTokenSaveMutation.Data>>

    suspend fun getCheckoutComSavedCardTokensAsync(): Deferred<Response<GetCustomerCardTokensQuery.Data>>

    suspend fun customerCardTokenDeleteAsync(cardId:String): Deferred<Response<CustomerCardTokenDeleteMutation.Data>>
}