package com.huafang.mvvm.di

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Activity 通用注入 Module
 * @author yang.guo on 2022/10/26
 */
@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
    @Provides
    fun providesLifecycle(activity: FragmentActivity): Lifecycle {
        return activity.lifecycle
    }
}