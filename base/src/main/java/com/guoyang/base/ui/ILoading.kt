package com.guoyang.base.ui

/**
 * @author yang.guo on 2022/10/26
 * @describe 加载框接口
 */
interface ILoading {
    /**
     * 显示Loading
     */
    fun showLoading(message: String)

    /**
     * 隐藏Loading
     */
    fun dismissLoading()
}