package com.huafang.module_home.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.dylanc.longan.launchAndCollectIn
import com.guoyang.base.ext.*
import com.guoyang.base.state.asUiStateFlow
import com.guoyang.base.state.doSuccess
import com.huafang.module_home.view.adapter.ContentAdapter
import com.huafang.module_home.databinding.HomeFragmentFollowBinding
import com.huafang.module_home.viewmodel.FollowViewModel
import com.huafang.mvvm.ext.bindBaseAdapter
import com.huafang.mvvm.ext.init
import com.huafang.mvvm.state.bindUiState
import com.huafang.mvvm.view.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * @author yang.guo on 2022/10/12
 * 关注页面
 */
@AndroidEntryPoint
class FollowFragment : BaseBindingFragment<HomeFragmentFollowBinding>() {
    @Inject
    lateinit var adapter: ContentAdapter

    private val followViewModel: FollowViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        binding.apply {
            recyclerView
                .linear()
                .divider { setDivider(6) }
                .bindBaseAdapter(this@FollowFragment.adapter)
            refreshLayout
                .init(
                    recyclerView = recyclerView,
                    stateLayout = stateLayout
                ) { isRefresh ->
                    loadData(isRefresh)
                }
        }
    }

    override fun lazyLoadData() {
        binding.stateLayout.showLoading()
    }

    private fun loadData(isRefresh: Boolean = true) {
        requestReadOrWritePermissions { allGranted, _, _ ->
            if (!allGranted) return@requestReadOrWritePermissions
            followViewModel.getFollowList()
                .asUiStateFlow()
                .launchAndCollectIn(viewLifecycleOwner) {
                    it.bindUiState(
                        isRefresh,
                        10,
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
}