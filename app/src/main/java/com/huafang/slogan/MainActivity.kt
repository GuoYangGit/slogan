package com.huafang.slogan

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import com.guoyang.utils_helper.getCompatColor
import com.guoyang.utils_helper.immersive
import com.guoyang.viewbinding_helper.doOnCustomTabSelected
import com.guoyang.viewbinding_helper.setCustomView
import com.huafang.mvvm.ComplianceInit
import com.huafang.mvvm.view.BaseBindingActivity
import com.huafang.mvvm.util.ARouterNavigation
import com.huafang.slogan.databinding.ActivityMainBinding
import com.huafang.slogan.databinding.LayoutBottomTabBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 首页
 * @author yang.guo on 2022/10/25
 */
@AndroidEntryPoint
@Route(path = ARouterNavigation.PATH_MAIN_ACTIVITY)
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    /**
     * 首页TabTitle
     */
    private val tabTitle = listOf(
        R.string.navigation_home, R.string.navigation_message, R.string.navigation_me
    )

    /**
     * 首页TabImages
     */
    private val tabImage = listOf(
        R.drawable.menu_home,
        R.drawable.menu_message,
        R.drawable.menu_me,
    )

    override fun initView(savedInstanceState: Bundle?) {
        binding.run {
            immersive(darkMode = true)
            // 设置ViewPager
            mainViewpager.run {
                // 设置不可以滑动
                isUserInputEnabled = false
                // 设置适配器
                adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
                    override fun getItemCount(): Int = tabTitle.size
                    override fun createFragment(position: Int): Fragment {
                        return when (position) {
                            0 -> ARouterNavigation.toHomeFragment()
                            1 -> ARouterNavigation.toMessageFragment()
                            else -> ARouterNavigation.toMeFragment()
                        }
                    }
                }
            }
            tabLayout.doOnCustomTabSelected<LayoutBottomTabBinding>(
                onTabSelected = {
                    tvTitle.setTextColor(getCompatColor(R.color.colorPrimary))
                },
                onTabUnselected = {
                    tvTitle.setTextColor(getCompatColor(R.color.content_color))
                })
            TabLayoutMediator(tabLayout, mainViewpager) { tab, position ->
                tab.setCustomView<LayoutBottomTabBinding> {
                    tvTitle.text = getString(tabTitle[position])
                    tvTitle.setTextColor(getCompatColor(if (position == 0) R.color.colorPrimary else R.color.content_color))
                    ivIcon.setImageResource(tabImage[position])
                }
            }.attach()
        }
        ComplianceInit.init()
    }
}