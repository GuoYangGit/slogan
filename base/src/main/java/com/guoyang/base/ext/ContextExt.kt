package com.guoyang.base.ext

import android.content.Context
import android.os.Process

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 14:33
 *  @description : [Context]相关的扩展类
 */
/**
 * 判断当前是否是主进程
 * @param block: 结果回调
 */
inline fun Context.runMainProcess(block: () -> Unit) {
    val myPid = Process.myPid()
    val mActivityManager =
        this.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
    val var3 = mActivityManager.runningAppProcesses?.iterator()
    while (var3?.hasNext() == true) {
        val appProcessInfo = var3.next() as android.app.ActivityManager.RunningAppProcessInfo
        if (appProcessInfo.pid == myPid && appProcessInfo.processName.equals(
                this.packageName, ignoreCase = true
            )
        ) {
            block()
            break
        }
    }
}