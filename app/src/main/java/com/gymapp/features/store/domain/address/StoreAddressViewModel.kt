package com.gymapp.features.store.domain.address

import androidx.lifecycle.MutableLiveData
import com.gymapp.base.domain.BaseViewModel
import com.gymapp.features.onboarding.auth.data.UserRepositoryInterface
import com.gymapp.main.data.model.user.AddressUser
import com.gymapp.main.data.repository.config.ConfigRepositoryInterface

class StoreAddressViewModel(
    val userRepositoryInterface: UserRepositoryInterface,
    val configRepositoryInterface: ConfigRepositoryInterface
) :
    BaseViewModel() {

    val addressesList = MutableLiveData<List<AddressUser>>()


    fun getAddresses() {
        val user = userRepositoryInterface.getCurrentUser() ?: return

        val countryId = configRepositoryInterface.getCountries().first {
            it.isoCode == "AE"
        }

        addressesList.value = user.address.filter {
            it.countryId == countryId.countryId
        }
    }

}