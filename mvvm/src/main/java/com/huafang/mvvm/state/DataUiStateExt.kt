package com.huafang.mvvm.state

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.drake.statelayout.StateLayout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 15:07
 *  @description : [DataUiState]扩展类
 */

/**
 * 成功请求回调
 * @param block 事件回调
 */
inline fun <T> UiState<T>.doSuccess(block: (data: T?) -> Unit): UiState<T> {
    if (this is UiState.Success) {
        block.invoke(data)
    }
    return this
}

/**
 * 错误请求回调
 * @param block 事件回调
 */
inline fun <T> UiState<T>.doError(block: (throwable: Throwable) -> Unit): UiState<T> {
    if (this is UiState.Error) {
        block.invoke(error)
    }
    return this
}

/**
 * 将Flow转换为状态布局Flow
 */
fun <T> Flow<T>.asUiStateFlow(
    isRefresh: Boolean = true
): Flow<UiState<T>> {
    return this.map {
        UiState.onSuccess(it, isRefresh)
    }.catch {
        this.emit(UiState.onError(it, isRefresh))
    }
}

/**
 * 绑定加载状态
 * @param swipeRefreshLayout 下拉刷新控件
 * @param adapter [BaseQuickAdapter]适配器
 * @param stateLayout 状态布局
 */
fun <T> UiState<T>.bindUiState(
    swipeRefreshLayout: SwipeRefreshLayout? = null,
    adapter: BaseQuickAdapter<*, *>? = null,
    stateLayout: StateLayout? = null,
): UiState<T> {
    // 判断是否有加载更多功能
    val isLoadMore = adapter is LoadMoreModule
    // 判断数据是否为空
    val isEmpty = when {
        this is UiState.Success && this.data is Collection<*> -> {
            this.data.isEmpty()
        }
        this is UiState.Success && this.data is IPageSate -> {
            this.data.hasEmpty()
        }
        this is UiState.Success -> {
            this.data == null
        }
        else -> {
            false
        }
    }
    // 判断是否还有更多数据
    val hasMore = when {
        this is UiState.Success && this.data is Collection<*> -> {
            this.data.size >= 10
        }
        this is UiState.Success && this.data is IPageSate -> {
            this.data.hasMore()
        }
        else -> {
            true
        }
    }
    this.doSuccess { // 请求成功
        when {
            refresh -> { // 下拉刷新
                swipeRefreshLayout?.isRefreshing = false
                if (isEmpty) {
                    stateLayout?.showEmpty()
                } else {
                    stateLayout?.showContent()
                }
            }
            isLoadMore -> { // 上拉加载更多
                if (!isEmpty && hasMore) {
                    adapter?.loadMoreModule?.loadMoreComplete()
                } else {
                    adapter?.loadMoreModule?.loadMoreEnd()
                }
                stateLayout?.showContent()
            }
        }
    }.doError { throwable ->
        when {
            refresh -> {
                swipeRefreshLayout?.isRefreshing = false
            }
            isLoadMore -> {
                adapter?.loadMoreModule?.loadMoreFail()
            }
        }
        stateLayout?.showError(throwable.message)
    }
    return this
}