package com.huafang.slogan

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.huafang.mvvm.ui.BaseBindingActivity
import com.huafang.mvvm.util.ARouterUtils
import com.huafang.slogan.databinding.ActivityMainBinding
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import com.zackratos.ultimatebarx.ultimatebarx.statusBarOnly
import dagger.hilt.android.AndroidEntryPoint

/**
 *  @author : yang.guo
 *  @date : 2022/10/10 17:14
 *  @description :
 */
@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        statusBarOnly {
            // 设置状态栏字体颜色
            light = true
            // 设置状态栏为透明色
            transparent()
        }
        // 设置ViewPager
        binding.mainViewpager.run {
            // 设置不可以滑动
            isUserInputEnabled = false
            // 设置适配器
            adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
                override fun getItemCount(): Int = 3
                override fun createFragment(position: Int): Fragment {
                    return when (position) {
                        0 -> ARouterUtils.toHomeFragment()
                        1 -> ARouterUtils.toMessageFragment()
                        else -> ARouterUtils.toMeFragment()
                    }
                }
            }
        }
        // 设置底部导航栏
        binding.bottomNavigationView.run {
            itemIconTintList = null
            // 设置选中监听
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_main -> binding.mainViewpager.setCurrentItem(0, false)
                    R.id.menu_message -> binding.mainViewpager.setCurrentItem(1, false)
                    R.id.menu_me -> binding.mainViewpager.setCurrentItem(2, false)
                }
                true
            }
        }
    }
}