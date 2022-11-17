package com.huafang.module_home

import android.os.Bundle
import com.guoyang.utils_helper.immersive
import com.huafang.module_home.databinding.HomeActivityMainBinding
import com.huafang.mvvm.view.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<HomeActivityMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        immersive(darkMode = true)
    }
}