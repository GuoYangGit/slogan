package com.huafang.mvvm

import com.guoyang.utils_helper.isAppDebug
import com.effective.android.anchors.*
import com.guoyang.base.BaseApp

/**
 * Application中间层
 * @author yang.guo on 2022/11/4
 */
open class MvvmApplication : BaseApp() {

    override fun onCreate() {
        super.onCreate()
        AnchorsManager.getInstance()
            .debuggable { isAppDebug }
            .taskFactory { AppTaskFactory() }
            .anchors {
                arrayOf(
                    TASK_AROUTER_INIT,
                    TASK_APP_INIT,
                    TASK_VIEW_INIT,
                    TASK_NET_INIT,
                    TASK_IMAGE_LOAD_INIT
                )
            }
            .graphics {
                TASK_AROUTER_INIT.sons(
                    TASK_APP_INIT.sons(
                        TASK_VIEW_INIT.sons(
                            TASK_NET_INIT,
                            TASK_IMAGE_LOAD_INIT
                        )
                    )
                )
                arrayOf(TASK_AROUTER_INIT)
            }
            .startUp()
    }
}