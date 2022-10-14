package com.huafang.mvvm

import com.dylanc.longan.isAppDebug
import com.effective.android.anchors.*
import com.guoyang.base.BaseApp
import com.huafang.mvvm.init.*
import rxhttp.RxHttpPlugins
import javax.inject.Inject

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 14:30
 *  @description :
 */
open class MvvmApplication : BaseApp() {
    @Inject
    lateinit var rxHttpPlugins: RxHttpPlugins

    override fun onCreate() {
        super.onCreate()
        AnchorsManager.getInstance()
            .debuggable { isAppDebug }
            .taskFactory { AppTaskFactory() }
            .graphics {
                TASK_AROUTER_INIT.sons(
                    TASK_APP_INIT, TASK_VIEW_INIT, TASK_IMAGE_LOAD_INIT
                )
                arrayOf(TASK_AROUTER_INIT)
            }
            .startUp()

        // 初始化网络请求
        rxHttpPlugins.setDebug(isAppDebug)

    }
}