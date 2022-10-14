package com.huafang.mvvm.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 14:08
 *  @description : App通用注入Module
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    /**
     * TODO 提供ImageLoader
     * @param appContext Application上下文
     */
}