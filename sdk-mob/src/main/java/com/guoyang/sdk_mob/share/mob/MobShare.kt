package com.guoyang.sdk_mob.share.mob

import android.content.Context
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.tencent.qzone.QZone
import cn.sharesdk.wechat.friends.Wechat
import cn.sharesdk.wechat.moments.WechatMoments
import com.guoyang.sdk_mob.share.*
import com.mob.MobSDK

/**
 * @author yang.guo on 2022/11/2
 * @describe
 */
internal object MobShare : IShare {
    /**
     * 初始化分享
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
        val shareParams = Platform.ShareParams().apply {
            this.shareType = getShareType(data.shareType)
            this.title = data.title
            this.titleUrl = data.url
            this.url = data.url
            this.text = data.text
            this.imageUrl = data.imageUrl
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
            ShareType.TEXT -> Platform.SHARE_TEXT
            ShareType.IMAGE -> Platform.SHARE_IMAGE
            ShareType.WEB -> Platform.SHARE_WEBPAGE
            ShareType.MUSIC -> Platform.SHARE_MUSIC
            ShareType.VIDEO -> Platform.SHARE_VIDEO
        }
    }
}