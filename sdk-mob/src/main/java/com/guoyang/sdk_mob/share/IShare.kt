package com.guoyang.sdk_mob.share

import android.content.Context

/**
 * @author yang.guo on 2022/11/2
 * @describe 分享接口
 */
interface IShare {
    /**
     * 初始化分享
     */
    fun init(context: Context)

    /**
     * 分享
     * @param sharePlatform 平台
     * @param shareData 分享数据
     * @param shareListener 分享回调
     */
    fun share(
        sharePlatform: SharePlatform,
        shareData: ShareData.() -> Unit,
        shareListener: ShareListener
    )
}