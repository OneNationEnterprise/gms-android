package com.gymapp.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId)