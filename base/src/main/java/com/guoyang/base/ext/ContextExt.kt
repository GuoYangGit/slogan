package com.guoyang.base.ext

import android.content.Context
import android.os.Process
import java.io.BufferedReader
import java.io.FileReader

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 14:33
 *  @description : Context相关的扩展类
 */
/**
 * 判断当前是否是主进程
 * @param block: 结果回调
 */
inline fun Context.isMainProcess(block: (Boolean) -> Unit) {
    try {
        val isMainProcess = packageName == getProcessName(Process.myPid())
        block(isMainProcess)
    } catch (e: Exception) {
        block(false)
    }
}

/**
 * 获取进程名称
 * @param pid: 进程ID
 */
fun getProcessName(pid: Int): String {
    try {
        BufferedReader(FileReader("/proc/$pid/cmdline")).use { reader ->
            var processName = reader.readLine()
            if (processName.isNotEmpty()) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}