package com.guoyang.sdk_file_transfer

/**
 * 文件操作状态枚举类
 */
enum class FileTransferState {
    IDLE,// 默认状态
    LOADING,// 进行中
    SUCCESS,// 成功
    FAILED,// 失败
    CANCELED,// 取消
    PAUSED,// 暂停
}

/**
 * 文件操作状态数据类
 * @constructor transfer 文件操作接口实现类
 */
sealed class TransferStateData(val transfer: ITransfer) {
    /**
     * 加载状态
     * @constructor transfer 文件操作接口实现类，progress 进度
     */
    class Loading(transfer: ITransfer, val progress: Int) : TransferStateData(transfer)

    /**
     * 成功状态
     * @constructor transfer 文件操作接口实现类，fileUrl 远程文件路径，filePath 本地文件路径
     */
    class Success(transfer: ITransfer, val fileUrl: String, val filePath: String) :
        TransferStateData(transfer)

    /**
     * 失败状态
     * @constructor transfer 文件操作接口实现类
     */
    class Failed(transfer: ITransfer) : TransferStateData(transfer)

    /**
     * 取消状态
     * @constructor transfer 文件操作接口实现类
     */
    class Canceled(transfer: ITransfer) : TransferStateData(transfer)
}