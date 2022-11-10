package com.huafang.mvvm.state

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.drake.statelayout.StateLayout
import com.guoyang.base.state.IPageSate
import com.guoyang.base.state.UiState
import com.guoyang.base.state.doError
import com.guoyang.base.state.doSuccess
import com.huafang.mvvm.ext.msg

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 15:07
 *  @description : [UiState]扩展类
 */

/**
 * 绑定加载状态
 * @param isRefresh true:下拉刷新、false:上拉加载(默认为下拉刷新)
 * @param pageSize 每页数量(默认为10)
 * @param swipeRefreshLayout 下拉刷新控件
 * @param adapter [BaseQuickAdapter]适配器
 * @param stateLayout 状态布局
 */
fun <T> UiState<T>.bindUiState(
    isRefresh: Boolean = true,
    pageSize: Int = 10,
    swipeRefreshLayout: SwipeRefreshLayout? = null,
    adapter: BaseQuickAdapter<*, *>? = null,
    stateLayout: StateLayout? = null,
): UiState<T> {
    // 判断是否有加载更多功能
    val isLoadMore = adapter is LoadMoreModule
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
            isRefresh -> {
                swipeRefreshLayout?.isRefreshing = false
            }
            isLoadMore -> {
                adapter?.loadMoreModule?.loadMoreFail()
            }
        }
        stateLayout?.showError(throwable.msg)
    }
    return this
}