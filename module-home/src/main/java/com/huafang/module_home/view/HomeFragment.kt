package com.huafang.module_home.view

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.dylanc.longan.getCompatColor
import com.dylanc.viewbinding.doOnCustomTabSelected
import com.dylanc.viewbinding.setCustomView
import com.google.android.material.tabs.TabLayoutMediator
import com.huafang.module_home.R
import com.huafang.module_home.databinding.HomeFragmentHomeBinding
import com.huafang.mvvm.databinding.LayoutTextViewTabBinding
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
    private val tabTitles by lazy {
        listOf(
            getString(R.string.home_follow),
            getString(R.string.home_recommend)
        )
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.run {
            homeTitleLayout.addStatusBarTopPadding()
            // 设置ViewPager
            homeViewpager.run {
                // 设置不可以滑动
                isUserInputEnabled = false
                // 设置适配器
                adapter = object : FragmentStateAdapter(childFragmentManager, lifecycle) {
                    override fun getItemCount(): Int = tabTitles.size
                    override fun createFragment(position: Int): Fragment {
                        return when (position) {
                            0 -> FollowFragment()
                            else -> RecommendFragment()
                        }
                    }
                }
            }
            //使用.attach()将TabLayout和ViewPager2进行绑定,如果没有这步操作将不会联动
            TabLayoutMediator(tabLayout, homeViewpager) { tab, position ->
                tab.setCustomView<LayoutTextViewTabBinding> {
                    val isSelect = position == 0
                    tvTitle.text = tabTitles[position]
                    tvTitle.textSize = 18f
                    tvTitle.setTextColor(getCompatColor(if (isSelect) R.color.main_color else R.color.content_color))
                    tvTitle.typeface = if (isSelect) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
                }
            }.attach()

            tabLayout.doOnCustomTabSelected<LayoutTextViewTabBinding>(
                onTabSelected = {
                    tvTitle.setTextColor(getCompatColor(R.color.main_color))
                    tvTitle.typeface = Typeface.DEFAULT_BOLD
                },
                onTabUnselected = {
                    tvTitle.setTextColor(getCompatColor(R.color.content_color))
                    tvTitle.typeface = Typeface.DEFAULT
                }
            )
        }
    }
}