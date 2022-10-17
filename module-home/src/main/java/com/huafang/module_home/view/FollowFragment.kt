package com.huafang.module_home.view

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dylanc.longan.dp
import com.dylanc.longan.viewLifecycleScope
import com.guoyang.base.ext.bindBaseAdapter
import com.guoyang.base.weight.decoration.SpaceItemDecoration
import com.huafang.module_home.adapter.ContentAdapter
import com.huafang.module_home.databinding.HomeFragmentFollowBinding
import com.huafang.module_home.viewmodel.FollowViewModel
import com.huafang.mvvm.ui.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author yang.guo on 2022/10/12
 * @describe 关注页面
 */
@AndroidEntryPoint
class FollowFragment : BaseBindingFragment<HomeFragmentFollowBinding>() {
    private val adapter by lazy {
        ContentAdapter()
    }

    private val followViewModel: FollowViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        binding.recyclerView.run {
            bindBaseAdapter(
                LinearLayoutManager(context),
                this@FollowFragment.adapter
            )
            addItemDecoration(SpaceItemDecoration(0, 6.dp.toInt(), false))
        }
        viewLifecycleScope.launchWhenCreated {
            followViewModel.getFollowList()
                .collect{
                    adapter.setList(it)
                }
        }
    }
}