package com.guoyang.sdk_file_transfer.cos

import android.content.Context
import com.guoyang.sdk_file_transfer.ITransferCallback
import com.guoyang.sdk_file_transfer.FileTransferState
import com.guoyang.sdk_file_transfer.upload.IUpload
import com.tencent.cos.xml.transfer.COSXMLUploadTask

/**
 * @author yang.guo on 2022/11/3
 * @describe 腾讯云上传任务类
 */
class COSUploadTask(context: Context, private val config: COSConfig) : IUpload {
    private val appContext = context.applicationContext

    // 上传任务
    private var uploadTask: COSXMLUploadTask? = null

    // 上传状态
    private var currentState: FileTransferState = FileTransferState.IDLE

    /**
     * 上传文件
     * @param filePath 上传文件路径
     * @param callback 回调
     */
    override fun upload(filePath: String, callback: ITransferCallback) {
        // 上传文件
        uploadTask = COSTransferManager.createTransferManager(appContext, config)
            .upload(config.bucket, config.cosPath, filePath, null)
            .bindListener(filePath, callback) {
                currentState = it
            }
    }

    /**
     * 取消上传
     */
    override fun cancel() {
        uploadTask?.cancel()
    }

    /**
     * 暂停上传
     */
    override fun pause() {
        uploadTask?.pause()
    }

    /**
     * 恢复上传
     */
    override fun resume() {
        uploadTask?.resume()
    }

    /**
     * 获取当前上传状态
     */
    override fun getCurrentState(): FileTransferState = currentState
}