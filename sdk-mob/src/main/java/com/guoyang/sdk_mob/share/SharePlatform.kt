package com.guoyang.sdk_mob.share

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author yang.guo on 2022/11/2
 * 分享相关数据类
 */

/**
 * 分享平台枚举
 */
enum class SharePlatform {
    WECHAT, WECHAT_MOMENTS, QQ, QZONE,
}

/**
 * 分享数据
 */
@Parcelize
data class ShareData(
    var shareType: ShareType = ShareType.TEXT,
    var text: String = "",
    var imageUrl: String = "",
    var title: String = "",
    var url: String = "",
) : Parcelable

/**
 * 分享类型
 */
enum class ShareType {
    TEXT, IMAGE, WEB, MUSIC, VIDEO
}