package com.guoyang.sdk_file_transfer

/**
 * 文件操作接口
 */
interface ITransfer {

    /**
     * 取消下载/上传
     */
    fun cancel()

    /**
     * 暂停下载/上传
     */
    fun pause()

    /**
     * 恢复下载/上传
     */
    fun resume()

    /**
     * 获取当前状态
     * @return 当前状态 [FileTransferState]
     */
    fun getCurrentState(): FileTransferState
}

/**
 * 文件操作回调
 */
interface ITransferCallback {
    /**
     * 成功
     * @param fileUrl 文件网络地址
     * @param filePath 文件路径
     */
    fun onSuccess(fileUrl: String, filePath: String)

    /**
     * 失败
     */
    fun onFailed()

    /**
     * 进度
     */
    fun onProgress(progress: Int) {}

    /**
     * 取消
     */
    fun onCancel() {}

    /**
     * 暂停
     */
    fun onPaused() {}

    /**
     * 恢复
     */
    fun onResumed() {}
}