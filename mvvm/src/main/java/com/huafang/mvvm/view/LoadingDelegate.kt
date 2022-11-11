package com.huafang.mvvm.view

import android.content.Context
import com.guoyang.base.ui.ILoading
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

/**
 * Loading加载框代理实现类
 * @author yang.guo on 2022/10/11
 */
class LoadingDelegate(private val context: Context) : ILoading {
    private var loadingPopupView: LoadingPopupView? = null

    /**
     * 显示加载框
     * @param message 加载框提示信息
     */
    override fun showLoading(message: String) {
        if (loadingPopupView == null) {
            loadingPopupView = XPopup.Builder(context).asLoading(message)
        }
        loadingPopupView?.show()
    }

    /**
     * 隐藏加载框
     */
    override fun dismissLoading() {
        loadingPopupView?.dismiss()
    }
}