package com.huafang.mvvm.ext

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.drake.statelayout.StateLayout
import com.guoyang.base.R

/**
 * 初始化 SwipeRefreshLayout
 * @param colorID: 颜色资源
 * @param block: 刷新回调
 */
inline fun SwipeRefreshLayout.init(
    @ColorRes colorID: Int = R.color.colorPrimary,
    recyclerView: RecyclerView? = null,
    stateLayout: StateLayout? = null,
    crossinline block: (isRefresh: Boolean) -> Unit
) {
    //设置主题颜色
    this.setColorSchemeColors(ContextCompat.getColor(this.context, colorID))
    //设置下拉刷新事件
    this.setOnRefreshListener {
        block(true)
    }
    //判断RecyclerView的Adapter是否支持上拉加载更多
    val adapter: BaseQuickAdapter<*, *>? = recyclerView?.adapter as? BaseQuickAdapter<*, *>
    adapter?.loadMoreModule?.setOnLoadMoreListener {
        block(false)
    }
    stateLayout?.onRefresh {
        block(true)
    }
}