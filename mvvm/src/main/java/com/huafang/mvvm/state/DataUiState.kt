package com.huafang.mvvm.state

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 15:01
 *  @description :
 */
sealed class DataUiState<out T>(val refresh: Boolean) {
    companion object {
        fun <T> onStart(isRefresh: Boolean = true): DataUiState<T> = Start(isRefresh)

        fun <T> onSuccess(data: T?, isRefresh: Boolean = true): DataUiState<T> =
            Success(data, isRefresh)

        fun <T> onError(error: Throwable, isRefresh: Boolean = true): DataUiState<T> =
            Error(error, isRefresh)
    }

    data class Start(val isRefresh: Boolean) : DataUiState<Nothing>(isRefresh)

    data class Success<out T>(val data: T?, val isRefresh: Boolean) : DataUiState<T>(isRefresh)

    data class Error(val error: Throwable, val isRefresh: Boolean) : DataUiState<Nothing>(isRefresh)
}