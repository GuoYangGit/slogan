package com.guoyang.base.ext

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.guoyang.base.R

/**
 * @author yang.guo on 2022/10/11
 * @describe 刷新布局扩展类
 */

/**
 * 初始化 SwipeRefreshLayout
 * @param colorID: 颜色资源
 * @param block: 刷新回调
 */
inline fun SwipeRefreshLayout.init(
    @ColorRes colorID: Int = R.color.colorPrimary,
    baseQuickAdapter: BaseQuickAdapter<*, *>? = null,
    crossinline block: (isRefresh: Boolean) -> Unit
) {
    //设置主题颜色
    this.setColorSchemeColors(ContextCompat.getColor(this.context, colorID))
    //设置下拉刷新事件
    this.setOnRefreshListener {
        block(true)
    }
    //设置上拉加载更多事件
    baseQuickAdapter?.loadMoreModule?.setOnLoadMoreListener {
        block(false)
    }
}