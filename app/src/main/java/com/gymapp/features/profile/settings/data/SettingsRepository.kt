package com.gymapp.features.profile.settings.data

import com.gymapp.base.data.BaseRepository
//import com.gymapp.main.data.db.GymDao
import com.gymapp.main.network.ApiManagerInterface

class SettingsRepository(apiManager: ApiManagerInterface/*, gymDao: GymDao*/) :
    BaseRepository(apiManager/*, gymDao*/), SettingsRepositoryInterface {

    override fun invalidateUserDataOnLogout() {
        user = null
    }

}