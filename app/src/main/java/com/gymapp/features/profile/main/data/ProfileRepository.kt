package com.gymapp.features.profile.main.data

import com.gymapp.base.data.BaseRepository
import com.gymapp.main.data.db.GymDao
import com.gymapp.main.network.ApiManagerInterface

class ProfileRepository(apiManager: ApiManagerInterface, gymDao: GymDao) :
    BaseRepository(apiManager, gymDao), ProfileRepositoryInterface {

}