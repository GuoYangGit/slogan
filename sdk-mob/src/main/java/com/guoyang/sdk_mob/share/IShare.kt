package com.guoyang.sdk_mob.share

import android.content.Context

/**
 * 分享接口
 * @author yang.guo on 2022/11/2
 */
interface IShare {
    /**
     * 初始化分享(请务必在用户授权《隐私政策》后再初始化)
     * @param context 上下文 [Context]
     */
    fun init(context: Context)

    /**
     * 分享
     * @param sharePlatform 平台 [SharePlatform]
     * @param shareData 分享数据 [ShareData]
     * @param shareListener 分享回调 [ShareListener]
     */
    fun share(
        sharePlatform: SharePlatform,
        shareData: ShareData.() -> Unit,
        shareListener: ShareListener
    )
}