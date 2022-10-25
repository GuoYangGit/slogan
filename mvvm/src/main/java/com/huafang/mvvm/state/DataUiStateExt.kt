package com.huafang.mvvm.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.ViewType
import com.huafang.mvvm.weight.state.ErrorViewDelegate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 15:07
 *  @description : [DataUiState]扩展类
 */

/**
 * 开始请求回调
 * @param block 事件回调
 */
inline fun <T> DataUiState<T>.doStart(block: () -> Unit): DataUiState<T> {
    if (this is DataUiState.Start) {
        block.invoke()
    }
    return this
}

/**
 * 成功请求回调
 * @param block 事件回调
 */
inline fun <T> DataUiState<T>.doSuccess(block: (data: T?) -> Unit): DataUiState<T> {
    if (this is DataUiState.Success) {
        block.invoke(data)
    }
    return this
}

/**
 * 错误请求回调
 * @param block 事件回调
 */
inline fun <T> DataUiState<T>.doError(block: (throwable: Throwable) -> Unit): DataUiState<T> {
    if (this is DataUiState.Error) {
        block.invoke(error)
    }
    return this
}

/**
 * 绑定加载状态
 * @param swipeRefreshLayout 下拉刷新控件
 * @param adapter [BaseQuickAdapter]适配器
 * @param loadingState 状态布局
 */
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
                loadingState?.updateView<ErrorViewDelegate>(ViewType.ERROR) {
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

// TODO 这里还可以进行改进
fun <T> Flow<T>.bindUiState(
    isRefresh: Boolean = false,
    uiState: MutableLiveData<DataUiState<T>>
): Flow<T> {
    return this.onStart {
        uiState.postValue(DataUiState.onStart(isRefresh = isRefresh))
    }.catch {
        uiState.postValue(DataUiState.onError(it, isRefresh))
    }
}

fun <T> Flow<T>.bindUiState(
    isRefresh: Boolean = false,
    uiState: MutableLiveData<DataUiState<T>>,
    viewModel: ViewModel
) {
    viewModel.viewModelScope.launch {
        this@bindUiState.bindUiState(isRefresh, uiState).collect {
            uiState.postValue(DataUiState.onSuccess(it, isRefresh))
        }
    }
}