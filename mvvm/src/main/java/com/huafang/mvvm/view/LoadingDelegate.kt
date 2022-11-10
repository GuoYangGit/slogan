package com.huafang.mvvm.view

import android.content.Context
import com.guoyang.base.ui.ILoading
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

/**
 * @author yang.guo on 2022/10/11
 *
 */
class LoadingDelegate(private val context: Context) : ILoading {
    private var loadingPopupView: LoadingPopupView? = null

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