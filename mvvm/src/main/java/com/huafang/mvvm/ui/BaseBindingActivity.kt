package com.huafang.mvvm.ui

import androidx.viewbinding.ViewBinding
import com.dylanc.loadingstateview.Decorative
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.LoadingStateDelegate
import com.dylanc.loadingstateview.OnReloadListener
import com.dylanc.viewbinding.base.ActivityBinding
import com.dylanc.viewbinding.base.ActivityBindingDelegate
import com.guoyang.base.ui.activity.BaseActivity

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 10:34
 *  @description : Activity封装基类
 */
abstract class BaseBindingActivity<VB : ViewBinding> : BaseActivity(),
    LoadingState by LoadingStateDelegate(), OnReloadListener, Decorative,
    ActivityBinding<VB> by ActivityBindingDelegate() {

    override fun userDataBinding(): Boolean = true

    override fun initDataBind() {
        setContentViewWithBinding()
        binding.root.decorate(this, this)
    }

    override fun layoutId(): Int = -1

    override fun showLoading(message: String) {
    }

    override fun dismissLoading() {
    }
}