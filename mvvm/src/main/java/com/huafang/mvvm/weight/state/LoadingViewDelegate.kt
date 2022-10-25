package com.huafang.mvvm.weight.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.huafang.mvvm.R
import com.huafang.mvvm.databinding.LayoutLoadingBinding

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 16:01
 *  @description : 加载视图
 */
class LoadingViewDelegate : LoadingStateView.ViewDelegate(ViewType.LOADING) {
    private var binding: LayoutLoadingBinding? = null

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View =
        LayoutLoadingBinding.inflate(inflater, parent, false).run {
            binding = this
            root
        }
}