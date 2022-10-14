package com.huafang.mvvm.ui

import android.content.Context
import android.os.Bundle
import com.guoyang.base.IView
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

/**
 * @author yang.guo on 2022/10/11
 * @describe
 */
class ViewDelegate(private val context: Context) : IView {
    private var loadingPopupView: LoadingPopupView? = null
    override fun layoutId(): Int = -1

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun showLoading(message: String) {
        if (loadingPopupView == null) {
            loadingPopupView = XPopup.Builder(context).asLoading(message)
        }
        loadingPopupView?.show()
    }

    override fun dismissLoading() {
        loadingPopupView?.dismiss()
    }
}