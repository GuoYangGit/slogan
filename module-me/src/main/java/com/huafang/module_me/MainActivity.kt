package com.huafang.module_me

import android.os.Bundle
import com.guoyang.utils_helper.immersive
import com.huafang.module_me.databinding.MeActivityMainBinding
import com.huafang.mvvm.view.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<MeActivityMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        immersive(darkMode = true)
    }
}