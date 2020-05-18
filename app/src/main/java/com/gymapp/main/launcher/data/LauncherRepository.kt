package com.gymapp.main.launcher.data

import com.gymapp.base.data.BaseRepository
import com.gymapp.main.data.db.GymDao
import com.gymapp.main.network.ApiManagerInterface

class LauncherRepository(apiManager: ApiManagerInterface, gymDao: GymDao)
    : BaseRepository(apiManager, gymDao), LauncherRepositoryInterface {

}