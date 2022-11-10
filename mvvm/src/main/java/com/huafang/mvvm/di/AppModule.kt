package com.huafang.mvvm.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 *  @author yang.guo on 2022/10/26
 *  @description : App通用注入Module
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule