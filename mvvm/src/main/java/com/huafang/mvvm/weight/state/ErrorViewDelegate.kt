package com.huafang.mvvm.weight.state

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.huafang.mvvm.R

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 16:07
 *  @description : 错误视图
 */
class ErrorViewDelegate : LoadingStateView.ViewDelegate(ViewType.ERROR) {

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View =
        inflater.inflate(R.layout.layout_error, parent, false).apply {
//            findViewById<View>(R.id.btn_reload).setOnClickListener {
//                onReloadListener?.onReload()
//            }
        }

    fun updateMsg(msg: String) {

    }
}