package com.huafang.module_home.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.guoyang.base.ext.*
import com.guoyang.base.state.asUiStateFlow
import com.guoyang.base.state.doError
import com.guoyang.base.state.doSuccess
import com.guoyang.utils_helper.launchAndCollectIn
import com.guoyang.xloghelper.xLogD
import com.huafang.module_home.view.adapter.ContentAdapter
import com.huafang.module_home.databinding.HomeFragmentFollowBinding
import com.huafang.module_home.viewmodel.FollowViewModel
import com.huafang.mvvm.view.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * 关注页面
 * @author yang.guo on 2022/10/12
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
                .adapter = this@FollowFragment.adapter
            pageLayout.onRefresh {
                xLogD("onRefresh")
                loadData(this.index)
            }
        }
    }

    override fun lazyLoadData() {
        binding.pageLayout.refreshing()
    }

    private fun loadData(index: Int = 0) {
        followViewModel.getFollowList(index)
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