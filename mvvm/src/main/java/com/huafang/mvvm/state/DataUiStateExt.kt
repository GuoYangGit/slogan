package com.huafang.mvvm.state

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.dylanc.loadingstateview.LoadingState
import com.huafang.mvvm.ext.showContent
import com.huafang.mvvm.ext.showError
import com.huafang.mvvm.ext.showLoading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

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
 * 将Flow转换为状态布局Flow
 */
fun <T> Flow<T>.asUiStateFlow(
    isRefresh: Boolean = true
): Flow<DataUiState<T>> {
    return this.map {
        DataUiState.onSuccess(it, isRefresh)
    }.onStart {
        this.emit(DataUiState.onStart(isRefresh = isRefresh))
    }.catch {
        this.emit(DataUiState.onError(it, isRefresh))
    }
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
    this.doStart {
        if (!refresh) return@doStart
        swipeRefreshLayout?.isRefreshing = true
        loadingState?.showLoading()
    }.doSuccess {
        when {
            refresh -> {
                swipeRefreshLayout?.isRefreshing = false
                loadingState?.showContent()
            }
            isLoadMore -> {
                adapter?.loadMoreModule?.loadMoreComplete()
            }
        }
    }.doError { throwable ->
        when {
            refresh -> {
                swipeRefreshLayout?.isRefreshing = false
                loadingState?.showError(throwable.message ?: "")
            }
            isLoadMore -> {
                adapter?.loadMoreModule?.loadMoreFail()
            }
        }
    }
    return this
}