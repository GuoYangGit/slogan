package com.huafang.mvvm.weight.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.huafang.mvvm.databinding.LayoutErrorBinding

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 16:07
 *  @description : 错误视图
 */
class ErrorViewDelegate : LoadingStateView.ViewDelegate(ViewType.ERROR) {
    private var binding: LayoutErrorBinding? = null

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View =
        LayoutErrorBinding.inflate(inflater, parent, false).run {
            binding = this
            tvError.setOnClickListener {
                onReloadListener?.onReload()
            }
            root
        }

    fun updateMsg(msg: String) {
        binding?.tvError?.text = msg
    }
}