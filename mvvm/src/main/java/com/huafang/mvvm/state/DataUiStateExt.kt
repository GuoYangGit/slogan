package com.huafang.mvvm.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.ViewType
import com.huafang.mvvm.weight.ErrorViewDelegate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

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

fun <T> DataUiState<T>.bindLoadState(
    swipeRefreshLayout: SwipeRefreshLayout? = null,
    adapter: BaseQuickAdapter<*, *>? = null,
    loadingState: LoadingState? = null
): DataUiState<T> {
    // 判断是否有加载更多功能
    val isLoadMore = adapter is LoadMoreModule
    doStart {
        if (!refresh) return@doStart
        swipeRefreshLayout?.isRefreshing = true
        if (loadingState?.getViewType() == ViewType.CONTENT) return@doStart
        loadingState?.showLoadingView()
    }
    doSuccess {
        when {
            refresh -> {
                swipeRefreshLayout?.isRefreshing = false
                loadingState?.showContentView()
            }
            isLoadMore -> {
                adapter?.loadMoreModule?.loadMoreComplete()
            }
        }
    }
    doError { throwable ->
        when {
            refresh -> {
                swipeRefreshLayout?.isRefreshing = false
                if (loadingState?.getViewType() == ViewType.CONTENT) return@doError
                loadingState?.updateView<ErrorViewDelegate>(ViewType.ERROR){
                    this.updateMsg(throwable.message ?: "")
                }
                loadingState?.showErrorView()
            }
            isLoadMore -> {
                adapter?.loadMoreModule?.loadMoreFail()
            }
        }
    }
    return this
}

fun <T> Flow<T>.bindUiState(
    isRefresh: Boolean = false, uiState: MutableLiveData<DataUiState<T>>
): Flow<T> {
    return this.onStart {
        uiState.postValue(DataUiState.onStart(isRefresh = isRefresh))
    }.catch {
        uiState.postValue(DataUiState.onError(it, isRefresh))
    }
}

fun <T> Flow<T>.bindUiState(
    isRefresh: Boolean = false, uiState: MutableLiveData<DataUiState<T>>, viewModel: ViewModel
) {
    viewModel.viewModelScope.launch {
        this@bindUiState.bindUiState(isRefresh, uiState).collect {
            uiState.postValue(DataUiState.onSuccess(it, isRefresh))
        }
    }
}