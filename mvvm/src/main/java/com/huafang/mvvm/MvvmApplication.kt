package com.huafang.mvvm

import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.longan.handleUncaughtException
import com.dylanc.longan.initLogger
import com.dylanc.longan.logDebug
import com.guoyang.base.BaseApp
import com.huafang.mvvm.weight.EmptyViewDelegate
import com.huafang.mvvm.weight.ErrorViewDelegate
import com.huafang.mvvm.weight.LoadingViewDelegate
import com.huafang.mvvm.weight.ToolbarViewDelegate
import dagger.hilt.android.HiltAndroidApp
import rxhttp.RxHttpPlugins
import java.util.concurrent.TimeoutException
import javax.inject.Inject

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 14:30
 *  @description :
 */
@HiltAndroidApp
class MvvmApplication : BaseApp() {
    @Inject
    lateinit var rxHttpPlugins: RxHttpPlugins

    override fun onCreate() {
        super.onCreate()
        val debug = BuildConfig.DEBUG
        // 初始化日志打印
        initLogger(debug)
        // 初始化网络请求
        rxHttpPlugins.setDebug(debug)
        // 注册状态布局
        LoadingStateView.setViewDelegatePool {
            register(
                ToolbarViewDelegate(), // 标题栏
                LoadingViewDelegate(), // 加载状态
                ErrorViewDelegate(), // 错误状态
                EmptyViewDelegate() // 空数据状态
            )
        }
        // 全局处理未捕获的异常
        handleUncaughtException { thread, throwable ->
            if (thread.name == "FinalizerWatchdogDaemon" && throwable is TimeoutException) {
                logDebug(throwable.message)
                return@handleUncaughtException
            }
        }
    }
}