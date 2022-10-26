package com.huafang.module_home.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.dylanc.longan.launchAndCollectIn
import com.guoyang.base.ext.bindBaseAdapter
import com.guoyang.base.ext.divider
import com.guoyang.base.ext.init
import com.guoyang.base.ext.linear
import com.huafang.module_home.view.adapter.ContentAdapter
import com.huafang.module_home.databinding.HomeFragmentFollowBinding
import com.huafang.module_home.viewmodel.FollowViewModel
import com.huafang.mvvm.state.asUiStateFlow
import com.huafang.mvvm.state.bindUiState
import com.huafang.mvvm.state.doSuccess
import com.huafang.mvvm.ui.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * @author yang.guo on 2022/10/12
 * @describe 关注页面
 */
@AndroidEntryPoint
class FollowFragment : BaseBindingFragment<HomeFragmentFollowBinding>() {
    @Inject
    lateinit var adapter: ContentAdapter

    private val followViewModel: FollowViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        binding.recyclerView
            .linear()
            .divider { setDivider(6) }
            .bindBaseAdapter(this@FollowFragment.adapter)
        binding.refreshLayout
            .init { isRefresh ->
                loadData(isRefresh)
            }
        adapter.loadMoreModule.setOnLoadMoreListener {
            loadData(false)
        }
        binding.stateLayout.onRefresh {
            loadData(true)
        }
    }

    override fun lazyLoadData() {
        binding.stateLayout.showLoading()
    }

    private fun loadData(isRefresh: Boolean = true) {
        followViewModel.getFollowList()
            .asUiStateFlow(isRefresh)
            .launchAndCollectIn(viewLifecycleOwner) {
                it.bindUiState(
                    binding.refreshLayout,
                    adapter,
                    binding.stateLayout
                ).doSuccess { list ->
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