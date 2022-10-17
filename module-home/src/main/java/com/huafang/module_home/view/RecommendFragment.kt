package com.huafang.module_home.view

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dylanc.longan.viewLifecycleScope
import com.guoyang.base.ext.bindBaseAdapter
import com.huafang.module_home.adapter.RecommendAdapter
import com.huafang.module_home.databinding.HomeFragmentRecommendBinding
import com.huafang.module_home.entity.RecommendEntity
import com.huafang.mvvm.ui.BaseBindingFragment
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

/**
 * @author yang.guo on 2022/10/14
 * @describe
 */
@AndroidEntryPoint
class RecommendFragment : BaseBindingFragment<HomeFragmentRecommendBinding>() {
    private val staggeredGridLayoutManager: StaggeredGridLayoutManager by lazy {
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    private val recommendAdapter: RecommendAdapter by lazy {
        RecommendAdapter()
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.run {
            //解决加载下一页后重新排列的问题
            staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            val checkForGapMethod =
                StaggeredGridLayoutManager::class.java.getDeclaredMethod("checkForGaps")
            val markItemDecorInsetsDirtyMethod =
                RecyclerView::class.java.getDeclaredMethod("markItemDecorInsetsDirty")
            checkForGapMethod.isAccessible = true
            markItemDecorInsetsDirtyMethod.isAccessible = true
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val result = checkForGapMethod.invoke(recyclerView.layoutManager) as Boolean
                    //如果发生了重新排序，刷新itemDecoration
                    if (result) {
                        markItemDecorInsetsDirtyMethod.invoke(recyclerView)
                    }
                }
            })
            recyclerView.bindBaseAdapter(staggeredGridLayoutManager, recommendAdapter)
        }

    }

    override fun lazyLoadData() {
        viewLifecycleScope.async(Dispatchers.IO) {
            PictureSelector.create(this@RecommendFragment)
                .dataSource(SelectMimeType.ofImage())
                .obtainMediaData {
                    val list = it.map { RecommendEntity(url = it.path) }
                    recommendAdapter.setList(list)
                }
        }
    }
}