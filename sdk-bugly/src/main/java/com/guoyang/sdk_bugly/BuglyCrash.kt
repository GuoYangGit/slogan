package com.guoyang.sdk_bugly

import android.content.Context
import android.os.Process
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


/**
 * @author yang.guo on 2022/11/2
 * Bugly崩溃上报实现类
 * https://bugly.qq.com/docs/user-guide/instruction-manual-android/?v=1.0.0
 */
internal object BuglyCrash : ICrash {

    /**
     * 初始化(请务必在用户授权《隐私政策》后再初始化)
     * @param context 上下文
     * @param config 崩溃上报配置
     */
    override fun init(context: Context, config: CrashConfig.() -> Unit) {
        val crashConfig = CrashConfig().apply(config)
        val appContext = context.applicationContext
        val strategy = UserStrategy(appContext).apply {
            // 获取当前包名
            val packageName = context.packageName
            // 获取当前进程名
            val processName = getProcessName(Process.myPid())
            // 设置是否为上报进程
            this.isUploadProcess = processName == null || processName == packageName
            // 设置App版本号
            this.appVersion = crashConfig.appVersion
            // 设置渠道号
            this.appChannel = crashConfig.appChannel
            // 设置设备ID
            this.deviceID = crashConfig.deviceId
            // 设置设备型号
            this.deviceModel = crashConfig.deviceModel
            // 最新版SDK支持trace文件采集和anr过程中的主线程堆栈信息采集，由于抓取堆栈的系统接口 Thread.getStackTrace 可能造成crash，建议只对少量用户开启
            // 设置anr时是否获取系统trace文件，默认为false
            this.isEnableCatchAnrTrace = false
            // 设置是否获取anr过程中的主线程堆栈，默认为true
            this.isEnableRecordAnrMainStack = false
        }
        CrashReport.initCrashReport(appContext, crashConfig.appId, crashConfig.isDebug, strategy)
    }

    /**
     * 设置用户ID
     */
    override fun setUserId(userId: String) {
        CrashReport.setUserId(userId)
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName: String = reader.readLine()
            if (processName.isNotEmpty()) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }
}