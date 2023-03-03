package com.huafang.mvvm.state

import com.drake.statelayout.StateLayout
import com.guoyang.base.state.IPageSate
import com.guoyang.base.state.UiState
import com.guoyang.base.state.doError
import com.guoyang.base.state.doSuccess
import com.huafang.mvvm.net.msg
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * 绑定加载状态
 * @param pageSize 每页数量(默认为10)
 * @param smartRefreshLayout [SmartRefreshLayout] 下拉刷新|上拉加载更多控件
 * @param stateLayout [StateLayout] 状态布局
 */
fun <T> UiState<T>.bindUiState(
    smartRefreshLayout: SmartRefreshLayout? = null,
    stateLayout: StateLayout? = null,
    pageSize: Int = 10,
): UiState<T> {
    // 判断当前是否为刷新状态
    val isRefresh = smartRefreshLayout?.isRefreshing ?: false
    // 判断当前是否为加载更多状态
    val isLoadMore = smartRefreshLayout?.isLoading ?: false
    this.doSuccess { // 请求成功
        // 判断数据是否为空
        val isEmpty = when (it) {
            is Collection<*> -> {
                it.isEmpty()
            }
            is IPageSate -> {
                it.hasEmpty()
            }
            else -> {
                it == null
            }
        }
        // 判断是否还有更多数据
        val hasMore = when (it) {
            is Collection<*> -> {
                it.size >= pageSize
            }
            is IPageSate -> {
                it.hasMore()
            }
            else -> {
                true
            }
        }
        when {
            isRefresh -> { // 下拉刷新
                if (isEmpty) {
                    stateLayout?.showEmpty()
                } else {
                    stateLayout?.showContent()
                }
                smartRefreshLayout?.finishRefresh()
            }
            isLoadMore -> { // 上拉加载更多
                stateLayout?.showContent()
                if (!isEmpty && hasMore) {
                    smartRefreshLayout?.finishLoadMore()
                } else {
                    smartRefreshLayout?.finishLoadMoreWithNoMoreData()
                }
            }
        }
    }.doError { throwable ->
        when {
            isRefresh -> {
                smartRefreshLayout?.finishRefresh(false)
            }
            isLoadMore -> {
                smartRefreshLayout?.finishLoadMore(false)
            }
        }
        stateLayout?.showError(throwable.msg)
    }
    return this
}