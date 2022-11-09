package com.guoyang.sdk_im.push.utils

import com.tencent.imsdk.common.IMLog
import com.guoyang.sdk_im.push.utils.PushLog

/**
 * @author yang.guo on 2022/11/9
 * @describe
 */
object PushLog : IMLog() {
    private const val TAG = "TUIOfflinePush"
    fun v(strInfo: String?) {
        v(TAG, strInfo)
    }

    fun d(strInfo: String?) {
        d(TAG, strInfo)
    }

    fun i(strInfo: String?) {
        i(TAG, strInfo)
    }

    fun w(strInfo: String?) {
        w(TAG, strInfo)
    }

    fun e(strInfo: String?) {
        e(TAG, strInfo)
    }
}