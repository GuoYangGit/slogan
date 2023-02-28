package com.huafang.module_home.viewmodel

import com.huafang.module_home.model.RecommendRepository
import com.huafang.mvvm.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import kotlin.random.Random

/**
 * 推荐ViewModel
 * @author yang.guo on 2022/10/25
 */
@HiltViewModel
class RecommendViewModel @Inject constructor(private val recommendRepository: RecommendRepository) :
    BaseViewModel() {

    fun getArticleList(page: Int = 0): Flow<List<Any>> {
        if (page > 0) {
            return recommendRepository.getArticleList(page)
        }
        val articleFlow = recommendRepository.getArticleList(page)
        val bannerFlow = recommendRepository.getBannerList()
        val topArticleFlow = recommendRepository.getTopArticleList()
        return combine(articleFlow, bannerFlow, topArticleFlow) { articleList, bannerList, topArticle ->
            val list = mutableListOf<Any>()
            list.addAll(topArticle)
            list.add(bannerList)
            list.addAll(articleList)
            list
        }
    }
}