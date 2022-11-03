package com.guoyang.sdk_file_transfer.upload

import com.guoyang.sdk_file_transfer.ITransfer
import com.guoyang.sdk_file_transfer.ITransferCallback

/**
 * @author yang.guo on 2022/11/3
 * @describe 上传接口类
 */
interface IUpload : ITransfer {

    /**
     * 上传文件
     * @param filePath 上传文件路径
     * @param callback 回调
     */
    fun upload(filePath: String, callback: ITransferCallback)
}