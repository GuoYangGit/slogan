package com.huafang.module_message

import android.os.Bundle
import com.guoyang.utils_helper.immersive
import com.huafang.module_message.databinding.MessageActivityMainBinding
import com.huafang.mvvm.view.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<MessageActivityMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        immersive(darkMode = true)
    }
}