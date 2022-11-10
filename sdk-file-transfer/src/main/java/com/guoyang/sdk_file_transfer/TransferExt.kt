package com.guoyang.sdk_file_transfer

import com.guoyang.sdk_file_transfer.download.IDownload
import com.guoyang.sdk_file_transfer.upload.IUpload
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.transform

/**
 * @author yang.guo on 2022/11/3
 * @describe 文件上传/下载扩展类(Flow调用形式)
 */
fun <T : ITransfer> Flow<T>.asTransferStateFlow(filePath: String): Flow<TransferStateData> {
    return this.transform { it.asFlow(filePath) }
}

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