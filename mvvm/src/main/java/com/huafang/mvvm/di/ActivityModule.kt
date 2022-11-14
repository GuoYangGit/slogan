package com.huafang.mvvm.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Activity 通用注入 Module
 * @author yang.guo on 2022/10/26
 */
@Module
@InstallIn(ActivityComponent::class)
class ActivityModule