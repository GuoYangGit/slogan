package com.huafang.module_home.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.huafang.mvvm.ext.bindBaseAdapter
import com.huafang.mvvm.ext.init
import com.guoyang.base.ext.staggered
import com.guoyang.base.state.asUiStateFlow
import com.guoyang.base.state.bindLoading
import com.guoyang.base.state.doSuccess
import com.guoyang.utils_helper.launchAndCollectIn
import com.huafang.module_home.view.adapter.RecommendAdapter
import com.huafang.module_home.databinding.HomeFragmentRecommendBinding
import com.huafang.module_home.viewmodel.RecommendViewModel
import com.huafang.mvvm.state.bindUiState
import com.huafang.mvvm.view.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 推荐页面Fragment
 * @author yang.guo on 2022/10/14
 */
@AndroidEntryPoint
class RecommendFragment : BaseBindingFragment<HomeFragmentRecommendBinding>() {

    @Inject
    lateinit var adapter: RecommendAdapter

    private val recommendViewModel: RecommendViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        binding.apply {
            recyclerView
                .staggered(2)
                .bindBaseAdapter(adapter)
            refreshLayout
                .init(
                    recyclerView = recyclerView,
                    stateLayout = stateLayout,
                ) { isRefresh ->
                    loadData(isRefresh)
                }
        }
    }

    override fun lazyLoadData() {
        binding.stateLayout.showLoading()
    }

    private fun loadData(isRefresh: Boolean = true) {
        recommendViewModel.getRecommendList(this@RecommendFragment)
            .asUiStateFlow()
            .launchAndCollectIn(viewLifecycleOwner) {
                it.bindLoading(this@RecommendFragment)
                    .bindUiState(
                        isRefresh,
                        10,
                        binding.refreshLayout,
                        adapter,
                        binding.stateLayout
                    )
                    .doSuccess { list ->
                        if (list == null) return@doSuccess
                        if (isRefresh) {
                            adapter.setDiffNewData(list.toMutableList())
                        } else {
                            adapter.addData(list)
                        }
                    }
            }
    }
}