package com.huafang.module_message

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.huafang.module_message.databinding.MessageFragmentMessageBinding
import com.huafang.mvvm.ui.BaseBindingFragment
import com.huafang.mvvm.util.ARouterUtils
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yang.guo on 2022/10/12
 * @describe 消息页面
 */
@AndroidEntryPoint
@Route(path = ARouterUtils.PATH_Message_FRAGMENT)
class MessageFragment : BaseBindingFragment<MessageFragmentMessageBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }
}