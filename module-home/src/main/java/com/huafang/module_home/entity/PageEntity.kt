package com.huafang.module_home.entity

data class PageEntity<T>(
    val curPage: Int = 0,
    val datas: List<T> = listOf(),
    val offset: Int = 0,
    val over: Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0
)