package com.huafang.mvvm.ext

import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.ViewType
import com.huafang.mvvm.weight.state.EmptyViewDelegate
import com.huafang.mvvm.weight.state.ErrorViewDelegate

/**
 * @author yang.guo on 2022/10/25
 * @describe 关于状态视图的扩展类
 */

/**
 * 显示加载视图
 */
fun LoadingState.showLoading() {
    showLoadingView()
}

/**
 * 显示内容视图
 */
fun LoadingState.showContent() {
    showContentView()
}

/**
 * 显示错误视图
 */
fun LoadingState.showError(msg: String) {
    updateView<ErrorViewDelegate>(ViewType.ERROR) {
        this.updateMsg(msg)
    }
    showErrorView()
}

/**
 * 显示空视图
 */
fun LoadingState.showEmpty(msg: String) {
    updateView<EmptyViewDelegate>(ViewType.ERROR) {
        this.updateMsg(msg)
    }
    showEmptyView()
}