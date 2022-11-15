package com.guoyang.sdk_giftview

import android.content.res.AssetManager
import android.graphics.Bitmap
import com.tencent.qgame.animplayer.mix.Resource
import java.io.File


/**
 * 礼物播放接口
 */
interface IGiftView {

    /**
     * 播放礼物
     * @param file 礼物本地文件
     */
    fun startPlay(file: File): IGiftView

    /**
     * 播放礼物
     * @param assetManager asset管理器
     * @param assetsPath asset路径
     */
    fun startPlay(assetManager: AssetManager, assetsPath: String): IGiftView

    /**
     * 停止播放
     */
    fun stopPlay(): IGiftView

    /**
     * 设置播放次数
     * @param count 播放次数
     */
    fun setLoopCount(count: Int): IGiftView

    /**
     * 设置是否静音
     * @param isMute 是否静音
     */
    fun setGiftMute(isMute: Boolean): IGiftView

    /**
     * 设置礼物播放的缩放模式
     * @param scaleType 缩放模式
     */
    fun setGiftScale(scaleType: ScaleType): IGiftView

    /**
     * 添加播放监听
     * @param onStart 播放开始
     * @param onEnd 播放结束
     */
    fun addAnimListener(onStart: () -> Unit = {}, onEnd: () -> Unit = {}): IGiftView

    /**
     * 添加融合动画
     * @param fetchText 获取文字
     * @param fetchImage 获取图片
     * @param releaseResource 播放完毕后的资源回收
     */
    fun addFetchResource(
        fetchText: (resource: Resource, result: (String?) -> Unit) -> Unit,
        fetchImage: (resource: Resource, result: (Bitmap?) -> Unit) -> Unit,
        releaseResource: (resources: List<Resource>) -> Unit
    ): IGiftView

    /**
     * 注册点击事件监听
     * @param onResourceClickListener 点击事件
     */
    fun setOnResourceClickListener(onResourceClickListener: (resource: Resource) -> Unit): IGiftView
}

/**
 * 礼物播放缩放模式枚举
 */
enum class ScaleType {
    CENTER_CROP, FIT_CENTER, FIT_XY,
}