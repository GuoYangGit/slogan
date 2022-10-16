package com.huafang.module_home.entity

import kotlin.random.Random

/**
 * @author yang.guo on 2022/10/15
 * @describe 推荐实体类
 */
data class RecommendEntity(
    val id: Long = 0,
    val url: String = "",
    val imageHeight: Int = Random.nextInt(200) + 200,
    val content: String = "#初秋 真的好喜欢秋天，我喜欢明媚的阳光...",
    val userEntity: UserEntity = UserEntity(),
    val likeNun: Int = 0,
)
