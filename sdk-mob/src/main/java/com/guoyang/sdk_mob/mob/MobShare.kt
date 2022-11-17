package com.guoyang.sdk_mob.mob

import android.content.Context
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.Platform.ShareParams
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.tencent.qzone.QZone
import cn.sharesdk.wechat.friends.Wechat
import cn.sharesdk.wechat.moments.WechatMoments
import com.guoyang.sdk_mob.share.*
import com.mob.MobSDK

/**
 * Mob分享实现类
 * @author yang.guo on 2022/11/2
 */
internal object MobShare : IShare {
    /**
     * 初始化分享(请务必在用户授权《隐私政策》后再初始化)
     * @param context 上下文 [Context]
     */
    override fun init(context: Context) {
        // 合规指南，用户同意隐私协议后调用
        MobSDK.submitPolicyGrantResult(true)
    }

    /**
     * 分享
     * @param sharePlatform 平台
     * @param shareData 分享数据
     * @param shareListener 分享回调
     */
    override fun share(
        sharePlatform: SharePlatform,
        shareData: ShareData.() -> Unit,
        shareListener: ShareListener
    ) {
        // 构建分享数据
        val data = ShareData().apply(shareData)
        // 构建Mob分享参数
        val shareParams = ShareParams().apply {
            this.shareType = getShareType(data.shareType)
            when (data.shareType) {
                ShareType.TEXT -> { // 文本
                    this.text = data.text
                }
                ShareType.IMAGE -> { // 图片
                    this.imagePath = data.imagePath
                    this.imageUrl = data.imageUrl
                }
                ShareType.MUSIC -> { // 音乐
                    this.title = data.title
                    this.text = data.text
                    this.imagePath = data.imagePath
                    this.imageUrl = data.imageUrl
                    this.musicUrl = data.url
                }
                ShareType.VIDEO -> { // 视频
                    this.title = data.title
                    this.text = data.text
                    this.imagePath = data.imagePath
                    this.imageUrl = data.imageUrl
                    this.videoUrl = data.url
                    this.filePath = data.filePath
                }
                ShareType.WEB -> { // 网页
                    this.title = data.title
                    this.text = data.text
                    this.imagePath = data.imagePath
                    this.imageUrl = data.imageUrl
                    this.url = data.url
                    this.titleUrl = data.url
                }
            }
        }
        // 获取分享平台
        val platform = getPlatform(sharePlatform)
        ShareSDK.getPlatform(platform).apply {
            platformActionListener = MobShareListener(shareListener)
            share(shareParams)
        }
    }

    /**
     * 获取分享平台
     * @param sharePlatform 分享平台枚举
     */
    private fun getPlatform(sharePlatform: SharePlatform): String {
        return when (sharePlatform) {
            SharePlatform.WECHAT -> Wechat.NAME
            SharePlatform.WECHAT_MOMENTS -> WechatMoments.NAME
            SharePlatform.QQ -> QQ.NAME
            SharePlatform.QZONE -> QZone.NAME
        }
    }

    /**
     * 获取分享类型
     * @param shareType 分享类型枚举
     */
    private fun getShareType(shareType: ShareType): Int {
        return when (shareType) {
            ShareType.TEXT -> Platform.SHARE_TEXT // 分享文本
            ShareType.IMAGE -> Platform.SHARE_IMAGE // 分享图片
            ShareType.WEB -> Platform.SHARE_WEBPAGE // 分享网页
            ShareType.MUSIC -> Platform.SHARE_MUSIC // 分享音乐
            ShareType.VIDEO -> Platform.SHARE_VIDEO // 分享视频
        }
    }
}