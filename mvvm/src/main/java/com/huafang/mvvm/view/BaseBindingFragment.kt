package com.huafang.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.FragmentBinding
import com.dylanc.viewbinding.base.FragmentBindingDelegate
import com.guoyang.base.ui.ILoading
import com.guoyang.base.ui.fragment.BaseFragment

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 10:36
 *  @description : Fragment封装基类
 */
abstract class BaseBindingFragment<VB : ViewBinding> : BaseFragment(),
    FragmentBinding<VB> by FragmentBindingDelegate(), ILoading {
    private val viewDelegate: ViewDelegate by lazy {
        ViewDelegate(requireContext())
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