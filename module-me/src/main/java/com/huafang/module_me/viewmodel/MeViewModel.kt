package com.huafang.module_me.viewmodel

import com.guoyang.sdk_file_transfer.TransferStateData
import com.huafang.module_me.model.MeRepository
import com.huafang.mvvm.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *
 * @author yang.guo on 2022/11/15
 */
@HiltViewModel
class MeViewModel @Inject constructor(private val meRepository: MeRepository) : BaseViewModel() {
    fun pushAvatar(avatarPath: String): Flow<TransferStateData> {
        return meRepository.pushAvatar(avatarPath)
    }

    fun pullAvatar(avatarPath: String) {
        meRepository.pullAvatar(avatarPath)
    }
}