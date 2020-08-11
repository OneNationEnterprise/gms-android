package com.gymapp.main.network

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.gym.*
import com.apollographql.apollo.gym.type.*
import com.gymapp.base.data.BaseApiManager
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient

class ApiManagerImpl(okHttpClient: OkHttpClient) : BaseApiManager(okHttpClient),
    ApiManagerInterface {

    override suspend fun getCountriesAsync(): Deferred<Response<CountriesQuery.Data>> {
        return graphQlNoAuthClient.query(CountriesQuery()).toDeferred()
    }

    override suspend fun registerUserAsync(input: RegisterCustomerInput): Deferred<Response<RegisterUserMutation.Data>> {
        return graphQlNoAuthClient.mutate(RegisterUserMutation(input)).toDeferred()
    }

    override suspend fun getUserDetailsByEmailAsync(email: String): Deferred<Response<CustomerByEmailQuery.Data>> {
        return graphQlClient.query(CustomerByEmailQuery()).toDeferred()
    }

    override suspend fun getGymsInRadiusAsync(input: GymsInRadiusFilter): Deferred<Response<GymsInRadiusQuery.Data>> {
        return graphQlNoAuthClient.query(
            GymsInRadiusQuery(
                PaginatorInput(0, 50),
                Input.fromNullable(input)
            )
        ).toDeferred()
    }

    override suspend fun getGymDetailAsync(id: String): Deferred<Response<GymQuery.Data>> {
        return graphQlNoAuthClient.query(GymQuery(id)).toDeferred()
    }

    override suspend fun getGymCategoriesAsync(input: Input<GymClassCategoryFilter>): Deferred<Response<GymClassCategoriesQuery.Data>> {
        return graphQlNoAuthClient.query(GymClassCategoriesQuery(input)).toDeferred()
    }

    override suspend fun getMembershipAsync(input: Input<MembershipsFilter>): Deferred<Response<MembershipsQuery.Data>> {
        return graphQlNoAuthClient.query(MembershipsQuery(input)).toDeferred()
    }

    override suspend fun getPassesAsync(input: Input<PassesFilter>): Deferred<Response<PassesQuery.Data>> {
        return graphQlNoAuthClient.query(PassesQuery(input)).toDeferred()
    }

    override suspend fun getClassesAsync(input: Input<GymClassesFilter>): Deferred<Response<ClassesQuery.Data>> {
        return graphQlNoAuthClient.query(ClassesQuery(input)).toDeferred()
    }

    override suspend fun getMedicalFormAsync(): Deferred<Response<GetMedicalFormQuery.Data>> {
        return graphQlClient.query(GetMedicalFormQuery()).toDeferred()
    }

    override suspend fun saveMedicalFormAsync(input: CustomerMedicalForm): Deferred<Response<SaveMedicalFormMutation.Data>> {
        return graphQlClient.mutate(SaveMedicalFormMutation(input)).toDeferred()
    }

    override suspend fun getStoreHomeAsync(input: StoreHomeInput?): Deferred<Response<StoreHomeQuery.Data>> {
        return graphQlNoAuthClient.query(StoreHomeQuery(Input.fromNullable(input))).toDeferred()
    }

    override suspend fun getProductsAsync(
        input: ProductsFilter,
        pagingInput: PaginatorInput
    ): Deferred<Response<ProductsQuery.Data>> {
        return graphQlNoAuthClient.query(ProductsQuery(Input.fromNullable(input), pagingInput))
            .toDeferred()
    }

    override suspend fun saveCustomerAsync(input: SaveCustomerInput): Deferred<Response<SaveCustomerMutation.Data>> {
        return graphQlClient.mutate(SaveCustomerMutation(input)).toDeferred()
    }

    override suspend fun saveCustomerAddressAsync(input: SaveCustomerAddressInput): Deferred<Response<SaveAddressMutation.Data>> {
        return graphQlClient.mutate(SaveAddressMutation(input)).toDeferred()
    }
}