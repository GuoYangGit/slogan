package com.guoyang.sdk_file_transfer.cos

import android.content.Context
import com.guoyang.sdk_file_transfer.ITransferCallback
import com.guoyang.sdk_file_transfer.FileTransferState
import com.guoyang.sdk_file_transfer.download.IDownload
import com.tencent.cos.xml.transfer.COSXMLDownloadTask

/**
 * @author yang.guo on 2022/11/3
 * @describe 腾讯云下载任务类
 */
class COSDownloadTask(context: Context, private val config: COSConfig) : IDownload {
    private val appContext = context.applicationContext

    // 下载任务
    private var downloadTask: COSXMLDownloadTask? = null

    // 当前状态
    private var currentState: FileTransferState = FileTransferState.IDLE

    override fun download(filePath: String, callback: ITransferCallback) {
        // 下载文件
        downloadTask = COSTransferManager.createTransferManager(appContext, config)
            .download(appContext, config.bucket, config.cosPath, filePath, null)
            .bindListener(filePath, callback) {
                currentState = it
            }
    }

    /**
     * 取消下载
     */
    override fun cancel() {
        downloadTask?.cancel()
    }

    /**
     * 暂停下载
     */
    override fun pause() {
        downloadTask?.pause()
    }

    /**
     * 恢复下载
     */
    override fun resume() {
        downloadTask?.resume()
    }

    /**
     * 获取当前下载状态
     */
    override fun getCurrentState(): FileTransferState = currentState
}