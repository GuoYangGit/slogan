package com.huafang.slogan

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dylanc.longan.getCompatColor
import com.dylanc.viewbinding.doOnCustomTabSelected
import com.dylanc.viewbinding.setCustomView
import com.google.android.material.tabs.TabLayoutMediator
import com.huafang.mvvm.view.BaseBindingActivity
import com.huafang.mvvm.util.ARouterNavigation
import com.huafang.slogan.databinding.ActivityMainBinding
import com.huafang.slogan.databinding.LayoutBottomTabBinding
import com.zackratos.ultimatebarx.ultimatebarx.statusBarOnly
import dagger.hilt.android.AndroidEntryPoint

/**
 *  @author yang.guo
 *  @date 2022/10/10 17:14
 *  @description 首页
 */
@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    // 首页TabTitle
    private val tabTitle = listOf(
        R.string.navigation_home, R.string.navigation_message, R.string.navigation_me
    )

    // 首页TabImages
    private val tabImage = listOf(
        R.drawable.menu_home,
        R.drawable.menu_message,
        R.drawable.menu_me,
    )

    override fun initView(savedInstanceState: Bundle?) {
        statusBarOnly {
            // 设置状态栏字体颜色
            light = true
            // 设置状态栏为透明色
            transparent()
        }
        binding.run {
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
    }
}