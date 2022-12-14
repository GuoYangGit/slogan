package com.huafang.module_home.viewmodel

import androidx.fragment.app.Fragment
import com.huafang.module_home.entity.RecommendEntity
import com.huafang.module_home.model.RecommendRepository
import com.huafang.mvvm.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 推荐ViewModel
 * @author yang.guo on 2022/10/25
 */
@HiltViewModel
class RecommendViewModel @Inject constructor(private val recommendRepository: RecommendRepository) :
    BaseViewModel() {

    fun getRecommendList(fragment: Fragment, index: Int): Flow<List<RecommendEntity>> {
        return recommendRepository.getRecommendList(fragment, index)
    }
}