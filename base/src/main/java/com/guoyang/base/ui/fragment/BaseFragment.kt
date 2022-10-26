package com.guoyang.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.guoyang.base.ui.IView

/***
 *  @author : yang.guo
 *  @date : 2022/10/11 10:52
 *  @description : 通用的Fragment基类
 */
abstract class BaseFragment : Fragment(), IView {

    //是否第一次加载
    private var isFirst: Boolean = true

    /**
     * 懒加载
     */
    open fun lazyLoadData() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        initView(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (!isFirst) return
        lazyLoadData()
        isFirst = false
    }
}