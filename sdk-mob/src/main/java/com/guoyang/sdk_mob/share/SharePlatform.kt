package com.guoyang.sdk_mob.share

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 分享平台枚举
 */
enum class SharePlatform {
    WECHAT, WECHAT_MOMENTS, QQ, QZONE,
}

/**
 * 分享类型枚举
 */
enum class ShareType {
    TEXT, IMAGE, MUSIC, VIDEO, WEB,
}

/**
 * 分享数据
 * 分享参数说明: https://www.mob.com/wiki/detailed?wiki=567&id=14
 */
@Parcelize
data class ShareData(
    var shareType: ShareType = ShareType.TEXT,
    var title: String = "", // 标题
    var text: String = "", // 文本
    var imageUrl: String = "", // 图片网络地址
    var imagePath: String = "", // 图片本地地址
    var filePath: String = "", // 文件本地地址(包括视频)
    var url: String = "", // 网页地址
) : Parcelable