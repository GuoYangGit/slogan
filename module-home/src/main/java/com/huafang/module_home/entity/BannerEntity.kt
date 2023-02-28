package com.huafang.module_home.entity

data class BannerEntity(
    val desc: String = "",
    val id: Int = 0,
    val imagePath: String = "",
    val isVisible: Int = 0,
    val order: Int = 0,
    val title: String = "",
    val type: Int = 0,
    val url: String = ""
)