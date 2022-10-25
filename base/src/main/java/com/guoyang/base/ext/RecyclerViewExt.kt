package com.guoyang.base.ext

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * @author yang.guo on 2022/10/24
 * @describe
 */
fun RecyclerView.staggered(spanCount: Int, orientation: Int): RecyclerView {
    val staggeredGridLayoutManager =
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    //解决加载下一页后重新排列的问题
    staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
    val checkForGapMethod = StaggeredGridLayoutManager::class.java.getDeclaredMethod("checkForGaps")
    val markItemDecorInsetsDirtyMethod =
        RecyclerView::class.java.getDeclaredMethod("markItemDecorInsetsDirty")
    checkForGapMethod.isAccessible = true
    markItemDecorInsetsDirtyMethod.isAccessible = true
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val result = checkForGapMethod.invoke(recyclerView.layoutManager) as Boolean
            //如果发生了重新排序，刷新itemDecoration
            if (result) {
                markItemDecorInsetsDirtyMethod.invoke(recyclerView)
            }
        }
    })
    layoutManager = staggeredGridLayoutManager
    return this
}