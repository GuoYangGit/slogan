package com.huafang.mvvm.view

import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ActivityBinding
import com.dylanc.viewbinding.base.ActivityBindingDelegate
import com.guoyang.base.ui.ILoading
import com.guoyang.base.ui.activity.BaseActivity

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 10:34
 *  @description : Activity封装基类
 */
abstract class BaseBindingActivity<VB : ViewBinding> : BaseActivity(),
    ActivityBinding<VB> by ActivityBindingDelegate(), ILoading {
    private val viewDelegate: ViewDelegate by lazy {
        ViewDelegate(this)
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