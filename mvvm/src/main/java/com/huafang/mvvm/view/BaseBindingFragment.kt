package com.huafang.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.guoyang.base.ui.ILoading
import com.guoyang.base.ui.fragment.BaseFragment
import com.guoyang.viewbinding_helper.FragmentBinding
import com.guoyang.viewbinding_helper.FragmentBindingDelegate

/**
 * Fragment封装基类
 * @author yang.guo on 2022/10/13
 */
abstract class BaseBindingFragment<VB : ViewBinding> : BaseFragment(),
    FragmentBinding<VB> by FragmentBindingDelegate(), ILoading {
    private val viewDelegate: LoadingDelegate by lazy {
        LoadingDelegate(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = createViewWithBinding(inflater, container)

    override fun layoutId(): Int = -1

    override fun showLoading(message: String) {
        viewDelegate.showLoading(message)
    }

    override fun dismissLoading() {
        viewDelegate.dismissLoading()
    }
}