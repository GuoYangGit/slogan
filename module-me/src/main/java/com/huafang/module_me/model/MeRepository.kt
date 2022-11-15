package com.huafang.module_me.model

import com.guoyang.sdk_file_transfer.FileTransferHelper
import com.guoyang.sdk_file_transfer.ITransferCallback
import com.guoyang.sdk_file_transfer.TransferStateData
import com.guoyang.sdk_file_transfer.asTransferStateFlow
import com.guoyang.sdk_file_transfer.cos.*
import com.guoyang.xloghelper.xLogD
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 *
 * @author yang.guo on 2022/11/15
 */
class MeRepository @Inject constructor() {
    private val cosConfig = COSConfig(
        "AKIDeK2avYzDCzYtb8zd6OVVTRet7C9xbGvY", // 临时密钥 SecretId
        "myAHH8QXUK3Yny51oQ0Z2KXedbsCLQNF", // 临时密钥 SecretKey
        "", // 临时密钥 token
        300L, //临时密钥有效截止时间戳，单位是秒
        "slogan-1258334160", // 存储桶名称
        "avatar/20211115/1636920000000.jpg", // 对象在存储桶中的位置标识符，即称对象键
        "ap-beijing" // 存储桶所在地域
    )

    /**
     * 上传头像
     */
    fun pushAvatar(avatarPath: String): Flow<TransferStateData> {
        return flow { emit(cosConfig) }
            .asDownloadFlow()
            .asTransferStateFlow(avatarPath)
    }

    /**
     * 下载头像
     */
    fun pullAvatar(avatarPath: String) {
        FileTransferHelper.createDownloadTask {
            COSDownloadTask(cosConfig)
        }.download(avatarPath, object : ITransferCallback {
            override fun onProgress(progress: Int) {
                xLogD("下载进度：$progress")
            }

            override fun onSuccess(fileUrl: String, filePath: String) {
                xLogD("下载成功：$fileUrl, $filePath")
            }

            override fun onFailed() {
                xLogD("下载失败")
            }
        })
    }
}