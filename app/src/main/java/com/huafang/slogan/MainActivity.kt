package com.huafang.slogan

import android.os.Bundle
import com.huafang.mvvm.ui.BaseBindingActivity
import com.huafang.slogan.databinding.ActivityMainBinding

/**
 *  @author : yang.guo
 *  @date : 2022/10/10 17:14
 *  @description :
 */
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        binding.tv.text = "guoyang"
    }
}