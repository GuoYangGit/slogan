package com.huafang.mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.dylanc.loadingstateview.Decorative
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.LoadingStateDelegate
import com.dylanc.loadingstateview.OnReloadListener
import com.dylanc.viewbinding.base.FragmentBinding
import com.dylanc.viewbinding.base.FragmentBindingDelegate
import com.guoyang.base.ui.fragment.BaseFragment

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 10:36
 *  @description : Fragment封装基类
 */
abstract class BaseBindingFragment<VB : ViewBinding>() : BaseFragment(),
    LoadingState by LoadingStateDelegate(), OnReloadListener, Decorative,
    FragmentBinding<VB> by FragmentBindingDelegate() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = createViewWithBinding(inflater, container).decorate(this, this)

    override fun layoutId(): Int = -1

    override fun showLoading(message: String) {
    }

    override fun dismissLoading() {
    }
}