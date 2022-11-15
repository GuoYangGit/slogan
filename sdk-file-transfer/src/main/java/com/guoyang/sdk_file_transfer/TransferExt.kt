package com.guoyang.sdk_file_transfer

import com.guoyang.sdk_file_transfer.download.IDownload
import com.guoyang.sdk_file_transfer.upload.IUpload
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*

/**
 * 文件上传/下载扩展类(Flow调用形式)
 */
fun <T : ITransfer> Flow<T>.asTransferStateFlow(filePath: String): Flow<TransferStateData> {
    return this.flatMapConcat { it.asFlow(filePath) }
}

/**
 * 文件上传/下载扩展类(Flow调用形式)
 */
fun <T : ITransfer> T.asFlow(filePath: String): Flow<TransferStateData> {
    return callbackFlow {
        val callback = object : ITransferCallback {
            override fun onSuccess(fileUrl: String, filePath: String) {
                trySendBlocking(TransferStateData.Success(this@asFlow, fileUrl, filePath))
                channel.close()
            }

            override fun onFailed() {
                trySendBlocking(TransferStateData.Failed(this@asFlow))
                channel.close()
            }

            override fun onProgress(progress: Int) {
                trySendBlocking(TransferStateData.Loading(this@asFlow, progress))
            }

            override fun onCancel() {
                trySendBlocking(TransferStateData.Canceled(this@asFlow))
                channel.close()
            }
        }
        when (this@asFlow) {
            is IDownload -> {
                download(filePath, callback)
            }
            is IUpload -> {
                upload(filePath, callback)
            }
            else -> {
                throw IllegalArgumentException("not support transfer type")
            }
        }
        awaitClose {}
    }
}

/**
 * [TransferStateData] 扩展函数, 判断当前是否加载状态
 */
fun TransferStateData.doOnLoading(block: (transfer: ITransfer, progress: Int) -> Unit): TransferStateData {
    if (this is TransferStateData.Loading) {
        block(transfer, progress)
    }
    return this
}

/**
 * [TransferStateData] 扩展函数, 判断当前是否成功状态
 */
fun TransferStateData.doOnSuccess(block: (transfer: ITransfer, fileUrl: String, filePath: String) -> Unit): TransferStateData {
    if (this is TransferStateData.Success) {
        block(transfer, fileUrl, filePath)
    }
    return this
}

/**
 * [TransferStateData] 扩展函数, 判断当前是否失败状态
 */
fun TransferStateData.doOnFailed(block: (transfer: ITransfer) -> Unit): TransferStateData {
    if (this is TransferStateData.Failed) {
        block(transfer)
    }
    return this
}

/**
 * [TransferStateData] 扩展函数, 判断当前是否取消状态
 */
fun TransferStateData.doOnCanceled(block: (transfer: ITransfer) -> Unit): TransferStateData {
    if (this is TransferStateData.Canceled) {
        block(transfer)
    }
    return this
}