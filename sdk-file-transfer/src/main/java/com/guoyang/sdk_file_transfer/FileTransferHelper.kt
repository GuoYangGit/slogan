package com.guoyang.sdk_file_transfer

import com.guoyang.sdk_file_transfer.download.IDownload
import com.guoyang.sdk_file_transfer.upload.IUpload

/**
 * @author yang.guo on 2022/11/3
 * @describe 文件上传/下载帮助类
 */
object FileTransferHelper {
    /**
     * 创建文件上传任务
     */
    fun createUploadTask(block: () -> IUpload): IUpload = block()

    /**
     * 创建文件下载任务
     */
    fun createDownloadTask(block: () -> IDownload): IDownload = block()
}