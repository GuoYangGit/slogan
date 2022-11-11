package com.huafang.module_message

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.guoyang.xloghelper.xLogD
import com.huafang.module_message.databinding.MessageFragmentMessageBinding
import com.huafang.mvvm.view.BaseBindingFragment
import com.huafang.mvvm.util.ARouterNavigation
import com.huafang.mvvm.weight.richinput.RichInputTextView
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yang.guo on 2022/10/12
 * 消息页面
 */
@AndroidEntryPoint
@Route(path = ARouterNavigation.PATH_Message_FRAGMENT)
class MessageFragment : BaseBindingFragment<MessageFragmentMessageBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        binding.richInput.setOnTopicEnterListener(object : RichInputTextView.OnTopicEnterListener {

            override fun onTopicEnter(topic: String?, topicPosition: Int) {
                xLogD("topic = $topic, topicPosition = $topicPosition")
            }

            override fun onTopicRemove() {
                xLogD("onTopicRemove")
            }
        })
    }
}