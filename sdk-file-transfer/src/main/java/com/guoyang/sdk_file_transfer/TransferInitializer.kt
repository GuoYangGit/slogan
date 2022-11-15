package com.guoyang.sdk_file_transfer

import android.content.Context
import androidx.startup.Initializer

/**
 * 文件传输初始化类
 * @author yang.guo on 2022/11/15
 */
class TransferInitializer : Initializer<Unit> {
    override fun dependencies() = emptyList<Class<Initializer<*>>>()

    override fun create(context: Context) {
        FileTransferHelper.appContext = context.applicationContext
    }
}