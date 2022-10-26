package com.huafang.module_home.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import com.dylanc.longan.launchAndCollectIn
import com.dylanc.longan.viewLifecycleScope
import com.guoyang.base.ext.bindBaseAdapter
import com.guoyang.base.ext.divider
import com.guoyang.base.ext.linear
import com.huafang.module_home.view.adapter.ContentAdapter
import com.huafang.module_home.databinding.HomeFragmentFollowBinding
import com.huafang.module_home.viewmodel.FollowViewModel
import com.huafang.mvvm.ui.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
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
        followViewModel.getFollowList()
            .launchAndCollectIn(viewLifecycleOwner) {
                adapter.setList(it)
            }

    }
}