package com.huafang.mvvm.state

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 15:07
 *  @description :
 */
inline fun <T> DataUiState<T>.doStart(block: () -> Unit): DataUiState<T> {
    if (this is DataUiState.Start) {
        block.invoke()
    }
    return this
}

inline fun <T> DataUiState<T>.doSuccess(block: (data: T?) -> Unit): DataUiState<T> {
    if (this is DataUiState.Success) {
        block.invoke(data)
    }
    return this
}

inline fun <T> DataUiState<T>.doError(block: (throwable: Throwable) -> Unit): DataUiState<T> {
    if (this is DataUiState.Error) {
        block.invoke(error)
    }
    return this
}