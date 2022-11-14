package com.huafang.mvvm.view

import androidx.viewbinding.ViewBinding
import com.guoyang.base.ui.ILoading
import com.guoyang.base.ui.activity.BaseActivity
import com.guoyang.viewbinding_helper.ActivityBinding
import com.guoyang.viewbinding_helper.ActivityBindingDelegate

/**
 * Activity封装基类
 * @author yang.guo on 2022/10/13
 */
abstract class BaseBindingActivity<VB : ViewBinding> : BaseActivity(),
    ActivityBinding<VB> by ActivityBindingDelegate(), ILoading {
    private val viewDelegate: LoadingDelegate by lazy {
        LoadingDelegate(this)
    }

    override fun userDataBinding(): Boolean = true

    override fun initDataBind() {
        setContentViewWithBinding()
    }

    override fun layoutId(): Int = -1

    override fun showLoading(message: String) {
        viewDelegate.showLoading(message)
    }

    override fun dismissLoading() {
        viewDelegate.dismissLoading()
    }
}