package com.guoyang.sdk_file_transfer.download

import com.guoyang.sdk_file_transfer.ITransfer
import com.guoyang.sdk_file_transfer.ITransferCallback

/**
 * 下载文件接口类
 * @author yang.guo on 2022/11/3
 */
interface IDownload : ITransfer {

    /**
     * 下载文件
     * @param filePath 本地存储路径
     * @param callback 回调
     */
    fun download(filePath: String, callback: ITransferCallback)
}
