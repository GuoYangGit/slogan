package com.guoyang.sdk_file_transfer.download

import com.guoyang.sdk_file_transfer.ITransfer
import com.guoyang.sdk_file_transfer.ITransferCallback

/**
 * @author yang.guo on 2022/11/3
 * 下载接口类
 */
interface IDownload : ITransfer {

    /**
     * 下载文件
     * @param filePath 本地存储路径
     * @param callback 回调
     */
    fun download(filePath: String, callback: ITransferCallback)
}
