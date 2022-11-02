package com.huafang.module_message

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.huafang.module_message.databinding.MessageFragmentMessageBinding
import com.huafang.mvvm.view.BaseBindingFragment
import com.huafang.mvvm.util.ARouterNavigation
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yang.guo on 2022/10/12
 * @describe 消息页面
 */
@AndroidEntryPoint
@Route(path = ARouterNavigation.PATH_Message_FRAGMENT)
class MessageFragment : BaseBindingFragment<MessageFragmentMessageBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }
}