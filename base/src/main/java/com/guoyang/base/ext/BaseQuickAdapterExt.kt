package com.guoyang.base.ext

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.chad.library.adapter.base.module.LoadMoreModule

/**
 * BaseQuickAdapter扩展类
 * @author Yang.Guo on 2021/5/31.
 * @link https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
var adapterLastClickTime = 0L

fun BaseQuickAdapter<*, *>.doOnItemClickListener(
    clickIntervals: Int = 0,
    action: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit
) {
    setOnItemClickListener { adapter, view, position ->
        val currentTime = System.currentTimeMillis()
        if (adapterLastClickTime != 0L && (currentTime - adapterLastClickTime < clickIntervals)) {
            return@setOnItemClickListener
        }
        adapterLastClickTime = currentTime
        action(adapter, view, position)
    }
}

/**
 * 给adapter拓展的，防止重复点击item
 */
var adapterChildLastClickTime = 0L
fun BaseQuickAdapter<*, *>.doOnItemChildClickListener(
    clickIntervals: Int = 0,
    action: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit
) {
    setOnItemChildClickListener { adapter, view, position ->
        val currentTime = System.currentTimeMillis()
        if (adapterChildLastClickTime != 0L && (currentTime - adapterChildLastClickTime < clickIntervals)) {
            return@setOnItemChildClickListener
        }
        adapterChildLastClickTime = currentTime
        action(adapter, view, position)
    }
}

/**
 * RecyclerView绑定BaseQuickAdapter
 */
fun RecyclerView.bindBaseAdapter(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: BaseQuickAdapter<*, *>,
    loadMoreListener: OnLoadMoreListener? = null
): RecyclerView {
    layoutManager = layoutManger
    adapter = bindAdapter
    // 这里需要判断Adapter是否实现了LoadMoreModule接口
    if (bindAdapter is LoadMoreModule)
        bindAdapter.loadMoreModule.setOnLoadMoreListener(loadMoreListener)
    return this
}