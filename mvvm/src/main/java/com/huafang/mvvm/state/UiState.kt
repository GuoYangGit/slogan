package com.huafang.mvvm.state

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 15:01
 *  @description : 数据和UI相关的状态类
 */
sealed class UiState<out T>(val refresh: Boolean) {
    companion object {
        fun <T> onSuccess(data: T?, isRefresh: Boolean = true): UiState<T> =
            Success(data, isRefresh)

        fun <T> onError(error: Throwable, isRefresh: Boolean = true): UiState<T> =
            Error(error, isRefresh)
    }

    data class Success<out T>(val data: T?, val isRefresh: Boolean) : UiState<T>(isRefresh)

    data class Error(val error: Throwable, val isRefresh: Boolean) : UiState<Nothing>(isRefresh)
}

interface IPageSate {
    /**
     * 是否还有更多数据
     */
    fun hasMore(): Boolean

    /**
     * 是否是空数据
     */
    fun hasEmpty(): Boolean
}