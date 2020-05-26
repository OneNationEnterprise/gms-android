package com.gymapp.features.homepage.data

import com.gymapp.base.data.BaseRepository
import com.gymapp.base.data.BaseRepositoryInterface
import com.gymapp.main.data.db.GymDao
import com.gymapp.main.network.ApiManagerInterface

class HomepageRepository(apiManager: ApiManagerInterface, gymDao: GymDao) :
    BaseRepository(apiManager, gymDao), HomepageRepositoryInterface {


}