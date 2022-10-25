package com.huafang.module_home.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.dylanc.longan.viewLifecycleScope
import com.guoyang.base.ext.bindBaseAdapter
import com.guoyang.base.ext.staggered
import com.huafang.module_home.view.adapter.RecommendAdapter
import com.huafang.module_home.databinding.HomeFragmentRecommendBinding
import com.huafang.module_home.viewmodel.RecommendViewModel
import com.huafang.mvvm.state.asUiStateFlow
import com.huafang.mvvm.state.bindLoadState
import com.huafang.mvvm.state.doSuccess
import com.huafang.mvvm.ui.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author yang.guo on 2022/10/14
 * @describe 推荐页面
 */
@AndroidEntryPoint
class RecommendFragment : BaseBindingFragment<HomeFragmentRecommendBinding>() {

    @Inject
    lateinit var recommendAdapter: RecommendAdapter

    private val recommendViewModel: RecommendViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        binding.recyclerView
            .staggered(2)
            .bindBaseAdapter(recommendAdapter)
    }

    override fun lazyLoadData() {
        viewLifecycleScope.launchWhenResumed {
            recommendViewModel.getRecommendList(this@RecommendFragment)
                .asUiStateFlow()
                .collect {
                    it.bindLoadState(
                        adapter = recommendAdapter,
                        loadingState = this@RecommendFragment
                    )
                        .doSuccess { list ->
                            recommendAdapter.setList(list)
                        }
                }
        }
    }
}