package com.guoyang.base

import android.os.Bundle

/**
 *  @author : yang.guo
 *  @date : 2022/10/10 17:31
 *  @description :Activity、Fragment的基类接口
 */
interface IView {
    /**
     * 获取布局文件ID
     */
    fun layoutId(): Int

    /**
     * 初始化数据
     */
    fun initView(savedInstanceState: Bundle?)

    /**
     * 显示Loading
     */
    fun showLoading(message: String)

    /**
     * 隐藏Loading
     */
    fun dismissLoading()
}