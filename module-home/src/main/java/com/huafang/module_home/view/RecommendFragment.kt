package com.huafang.module_home.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.guoyang.base.ext.requestReadOrWritePermissions
import com.guoyang.base.ext.staggered
import com.guoyang.base.state.asUiStateFlow
import com.guoyang.base.state.doError
import com.guoyang.base.state.doSuccess
import com.guoyang.utils_helper.launchAndCollectIn
import com.huafang.module_home.view.adapter.RecommendAdapter
import com.huafang.module_home.databinding.HomeFragmentRecommendBinding
import com.huafang.module_home.viewmodel.RecommendViewModel
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
                .adapter = this@RecommendFragment.adapter
            pageLayout.onRefresh {
                loadData(index)
            }
        }
    }

    override fun lazyLoadData() {
        binding.pageLayout.refreshing()
    }

    private fun loadData(index: Int = 0) {
        requestReadOrWritePermissions { allGranted, _, _ ->
            if (!allGranted) return@requestReadOrWritePermissions
            recommendViewModel.getRecommendList(this@RecommendFragment, index)
                .asUiStateFlow()
                .launchAndCollectIn(viewLifecycleOwner) {
                    it.doSuccess { list ->
                        binding.pageLayout.addData(list) {
                            (list?.size ?: 0) == 10
                        }
                    }.doError { throwable ->
                        binding.pageLayout.showError(throwable.message)
                    }
                }
        }
    }
}