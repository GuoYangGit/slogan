package com.guoyang.sdk_file_transfer.cos

import android.content.Context
import android.os.Parcelable
import com.tencent.cos.xml.CosXmlService
import com.tencent.cos.xml.CosXmlServiceConfig
import com.tencent.cos.xml.transfer.TransferConfig
import com.tencent.cos.xml.transfer.TransferManager
import com.tencent.qcloud.core.auth.BasicLifecycleCredentialProvider
import com.tencent.qcloud.core.auth.SessionQCloudCredentials
import kotlinx.parcelize.Parcelize

/**
 * @author yang.guo on 2022/11/3
 * 腾讯云对象存储管理类
 */
object COSTransferManager {

    /**
     * 创建腾讯云对象存储服务
     * @param context 上下文
     * @param config 配置
     */
    fun createTransferManager(context: Context, config: COSConfig): TransferManager {
        // 创建临时密钥
        val provider = object : BasicLifecycleCredentialProvider() {
            override fun fetchNewCredentials(): SessionQCloudCredentials {
                return SessionQCloudCredentials(
                    config.secretId, config.secretKey, config.token, config.expiredTime
                )
            }
        }
        // 创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
        val serviceConfig = CosXmlServiceConfig.Builder().setRegion(config.region) // 设置一个默认的存储桶地域
            .isHttps(true) // 使用 https 请求, 默认 http 请求
            .builder()
        // 初始化 COS Service，获取实例
        val cosXmlService = CosXmlService(context.applicationContext, serviceConfig, provider)
        // 创建配置类
        val transferConfig = TransferConfig.Builder().build()
        // 初始化 TransferManager
        return TransferManager(cosXmlService, transferConfig)
    }
}


/**
 * 腾讯云对象存储配置类
 */
@Parcelize
data class COSConfig(
    val secretId: String, // 临时密钥 SecretId
    val secretKey: String, // 临时密钥 SecretKey
    val token: String, // 临时密钥 token
    val expiredTime: Long, //临时密钥有效截止时间戳，单位是秒
    val bucket: String, // 存储桶名称
    val cosPath: String, // 对象在存储桶中的位置标识符，即称对象键
    val region: String = DEFAULT_REGION, // 存储桶所在地域
) : Parcelable {
    companion object {
        const val DEFAULT_REGION = "ap-guangzhou"
    }
}