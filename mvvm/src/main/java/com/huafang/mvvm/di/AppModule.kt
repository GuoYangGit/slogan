package com.huafang.mvvm.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * App 通用注入 Module
 * @author yang.guo on 2022/10/26
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule