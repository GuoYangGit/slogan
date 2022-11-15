package com.guoyang.sdk_file_transfer

import android.content.Context
import com.guoyang.sdk_file_transfer.download.IDownload
import com.guoyang.sdk_file_transfer.upload.IUpload

/**
 * 文件上传/下载帮助类
 * @author yang.guo on 2022/11/3
 */
object FileTransferHelper {
    /**
     * App上下文
     */
    lateinit var appContext: Context

    /**
     * 创建文件上传任务
     */
    fun createUploadTask(block: () -> IUpload): IUpload = block()

    /**
     * 创建文件下载任务
     */
    fun createDownloadTask(block: () -> IDownload): IDownload = block()
}