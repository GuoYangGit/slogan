package com.huafang.mvvm.weight

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.huafang.mvvm.R

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 16:09
 *  @description : 空数据视图
 */
class EmptyViewDelegate : LoadingStateView.ViewDelegate(ViewType.EMPTY) {

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View =
        inflater.inflate(R.layout.layout_empty, parent, false)
}