package com.huafang.mvvm.weight.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.huafang.mvvm.databinding.LayoutEmptyBinding

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 16:09
 *  @description : 空数据视图
 */
class EmptyViewDelegate : LoadingStateView.ViewDelegate(ViewType.EMPTY) {
    private var binding: LayoutEmptyBinding? = null

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View =
        LayoutEmptyBinding.inflate(inflater, parent, false).run {
            binding = this
            tvEmpty.setOnClickListener {
                onReloadListener?.onReload()
            }
            root
        }

    fun updateMsg(msg: String) {
        binding?.tvEmpty?.text = msg
    }
}