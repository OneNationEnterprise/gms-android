package com.gymapp.base.domain

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.gymapp.main.GymApplication
import com.gymapp.main.network.ApiManagerInterface

open class BaseViewModel() : ViewModel()