package com.huafang.mvvm.state

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.drake.statelayout.StateLayout
import com.guoyang.base.ui.ILoading
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
inline fun <T> UiState<T>.doStart(block: () -> Unit): UiState<T> {
    if (this is UiState.Start) {
        block.invoke()
    }
    return this
}


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
fun <T> Flow<T>.asUiStateFlow(): Flow<UiState<T>> {
    return this.map {
        UiState.onSuccess(it)
    }.onStart {
        this.emit(UiState.onStart())
    }.catch {
        this.emit(UiState.onError(it))
    }
}

/**
 * 绑定Loading框
 * @param iLoading 具体实现
 * @param msg 加载显示内容(默认为空)
 */
fun <T> UiState<T>.bindLoading(iLoading: ILoading, msg: String = ""): UiState<T> {
    doStart {
        iLoading.showLoading(msg)
    }.doSuccess {
        iLoading.dismissLoading()
    }.doError {
        iLoading.dismissLoading()
    }
    return this
}

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
            this.data.size >= pageSize
        }
        this is UiState.Success && this.data is IPageSate -> {
            this.data.hasMore()
        }
        else -> {
            true
        }
    }
    doSuccess { // 请求成功
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
        stateLayout?.showError(throwable.message)
    }
    return this
}