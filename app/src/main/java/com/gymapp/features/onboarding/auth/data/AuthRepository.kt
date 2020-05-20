package com.gymapp.features.onboarding.auth.data

import com.gymapp.base.data.BaseRepository
import com.gymapp.main.data.db.GymDao
import com.gymapp.main.network.ApiManagerInterface

class AuthRepository(apiManager: ApiManagerInterface, gymDao: GymDao) :
    BaseRepository(apiManager, gymDao), AuthRepositoryInterface {

}
