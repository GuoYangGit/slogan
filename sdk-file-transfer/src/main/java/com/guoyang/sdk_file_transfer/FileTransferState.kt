package com.guoyang.sdk_file_transfer

/**
 * @author yang.guo on 2022/11/3
 * @describe 文件操作状态
 */
enum class FileTransferState {
    IDLE,// 默认状态
    LOADING,// 进行中
    SUCCESS,// 成功
    FAILED,// 失败
    CANCELED,// 取消
    PAUSED,// 暂停
}

sealed class TransferStateData(val transfer: ITransfer) {
    class Loading(transfer: ITransfer, val progress: Int) : TransferStateData(transfer)
    class Success(transfer: ITransfer, val fileUrl: String, val filePath: String) :
        TransferStateData(transfer)

    class Failed(transfer: ITransfer) : TransferStateData(transfer)
    class Canceled(transfer: ITransfer) : TransferStateData(transfer)
}

fun TransferStateData.doOnLoading(block: (ITransfer, Int) -> Unit): TransferStateData {
    if (this is TransferStateData.Loading) {
        block(transfer, progress)
    }
    return this
}

fun TransferStateData.doOnSuccess(block: (ITransfer, String, String) -> Unit): TransferStateData {
    if (this is TransferStateData.Success) {
        block(transfer, fileUrl, filePath)
    }
    return this
}

fun TransferStateData.doOnFailed(block: (ITransfer) -> Unit): TransferStateData {
    if (this is TransferStateData.Failed) {
        block(transfer)
    }
    return this
}

fun TransferStateData.doOnCanceled(block: (ITransfer) -> Unit): TransferStateData {
    if (this is TransferStateData.Canceled) {
        block(transfer)
    }
    return this
}