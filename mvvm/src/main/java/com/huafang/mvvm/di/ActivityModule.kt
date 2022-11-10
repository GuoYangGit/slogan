package com.huafang.mvvm.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * @author yang.guo on 2022/10/26
 * @describe Activity通用注入Module
 */
@Module
@InstallIn(ActivityComponent::class)
class ActivityModule