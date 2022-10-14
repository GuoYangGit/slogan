package com.huafang.module_home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.huafang.module_home.databinding.HomeFragmentHomeBinding
import com.huafang.mvvm.ui.BaseBindingFragment
import com.huafang.mvvm.util.ARouterUtils
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yang.guo on 2022/10/12
 * @describe
 */
@AndroidEntryPoint
@Route(path = ARouterUtils.PATH_HOME_FRAGMENT)
class HomeFragment : BaseBindingFragment<HomeFragmentHomeBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        binding.homeTitleLayout.addStatusBarTopPadding()
        // 设置ViewPager
        binding.homeViewpager.run {
            // 设置适配器
            adapter = object : FragmentStateAdapter(childFragmentManager, lifecycle) {
                override fun getItemCount(): Int = 2
                override fun createFragment(position: Int): Fragment {
                    return when (position) {
                        0 -> FollowFragment()
                        else -> RecommendFragment()
                    }
                }
            }
        }
        ViewPager2Delegate.install(binding.homeViewpager, binding.tabLayout)
    }
}