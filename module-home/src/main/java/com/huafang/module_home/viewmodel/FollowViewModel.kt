package com.huafang.module_home.viewmodel

import com.huafang.module_home.entity.ContentEntity
import com.huafang.module_home.model.FollowRepository
import com.huafang.mvvm.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author yang.guo on 2022/10/14
 * @describe 关注ViewModel
 */
@HiltViewModel
class FollowViewModel @Inject constructor(private val followRepository: FollowRepository) :
    BaseViewModel() {

    /**
     * 获取关注列表页面
     */
    fun getFollowList(): Flow<List<ContentEntity>> {
        return followRepository.getFollowList()
    }
}