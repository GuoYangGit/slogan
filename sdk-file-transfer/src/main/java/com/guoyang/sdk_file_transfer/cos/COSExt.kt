package com.guoyang.sdk_file_transfer.cos

import android.content.Context
import com.guoyang.sdk_file_transfer.FileTransferHelper
import com.guoyang.sdk_file_transfer.FileTransferState
import com.guoyang.sdk_file_transfer.ITransferCallback
import com.guoyang.sdk_file_transfer.download.IDownload
import com.guoyang.sdk_file_transfer.upload.IUpload
import com.tencent.cos.xml.exception.CosXmlClientException
import com.tencent.cos.xml.exception.CosXmlServiceException
import com.tencent.cos.xml.listener.CosXmlResultListener
import com.tencent.cos.xml.model.CosXmlRequest
import com.tencent.cos.xml.model.CosXmlResult
import com.tencent.cos.xml.transfer.COSXMLTask
import com.tencent.cos.xml.transfer.TransferState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author yang.guo on 2022/11/3
 * 腾讯云对象存储任务扩展类
 */
/**
 * 扩展方法，腾讯云对象存储任务回调绑定
 */
inline fun <T : COSXMLTask> T.bindListener(
    filePath: String,
    callback: ITransferCallback,
    crossinline state: (state: FileTransferState) -> Unit
): T {
    // 设置上传进度回调
    this.setCosXmlProgressListener { complete, target ->
        callback.onProgress((complete * 100 / target).toInt())
    }
    // 设置上传结果回调
    this.setCosXmlResultListener(object : CosXmlResultListener {
        override fun onSuccess(request: CosXmlRequest?, result: CosXmlResult?) {
            val uploadUrl = result?.accessUrl ?: ""
            callback.onSuccess(uploadUrl, filePath)
        }

        override fun onFail(
            request: CosXmlRequest?,
            clientException: CosXmlClientException?,
            serviceException: CosXmlServiceException?
        ) {
            callback.onFailed()
        }
    })
    // 设置上传状态回调
    this.setTransferStateListener {
        when (it) {
            TransferState.WAITING, TransferState.IN_PROGRESS -> { // 等待中
                state(FileTransferState.LOADING)
            }
            TransferState.PAUSED -> { // 暂停中
                state(FileTransferState.PAUSED)
                callback.onPaused()
            }
            TransferState.RESUMED_WAITING -> { // 恢复等待中
                callback.onResumed()
            }
            TransferState.COMPLETED -> { // 上传完成
                state(FileTransferState.SUCCESS)
            }
            TransferState.CANCELED -> { // 取消中
                state(FileTransferState.CANCELED)
                callback.onCancel()
            }
            TransferState.CONSTRAINED, TransferState.FAILED -> { // 上传失败
                state(FileTransferState.FAILED)
            }
            TransferState.UNKNOWN -> { // 未知状态
            }
            else -> {}
        }
    }
    return this
}

fun Flow<COSConfig>.asUploadFlow(context: Context): Flow<IUpload> {
    return this.map { config ->
        FileTransferHelper.createUploadTask {
            COSUploadTask(context, config)
        }
    }
}

fun Flow<COSConfig>.asDownloadFlow(context: Context): Flow<IDownload> {
    return this.map { config ->
        FileTransferHelper.createDownloadTask {
            COSDownloadTask(context, config)
        }
    }
}