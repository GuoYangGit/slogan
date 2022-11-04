package com.guoyang.sdk_giftview

import android.content.res.AssetManager
import android.graphics.Bitmap
import com.tencent.qgame.animplayer.mix.Resource
import java.io.File


/**
 * @author yang.guo on 2022/11/4
 * @describe 礼物播放接口
 */
interface IGiftView {

    /**
     * 播放礼物
     * @param file 礼物本地文件
     */
    fun startPlay(file: File)

    /**
     * 播放礼物
     * @param assetManager asset管理器
     * @param assetsPath asset路径
     */
    fun startPlay(assetManager: AssetManager, assetsPath: String)

    /**
     * 停止播放
     */
    fun stopPlay()

    /**
     * 设置播放次数
     * @param count 播放次数
     */
    fun setLoop(count: Int)

    /**
     * 设置是否静音
     * @param isMute 是否静音
     */
    fun setMute(isMute: Boolean)

    /**
     * 设置礼物播放的缩放模式
     * @param scaleType 缩放模式
     */
    fun setScale(scaleType: ScaleType)

    /**
     * 添加播放监听
     * @param onStart 播放开始
     * @param onEnd 播放结束
     */
    fun addAnimListener(onStart: () -> Unit = {}, onEnd: () -> Unit = {})

    /**
     * 添加融合动画
     * @param fetchText 获取文字
     * @param fetchImage 获取图片
     * @param fetchOnClickListener 获取点击事件
     */
    fun addFetchResource(
        fetchText: (resource: Resource, result: (String?) -> Unit) -> Unit,
        fetchImage: (resource: Resource, result: (Bitmap?) -> Unit) -> Unit,
        fetchOnClickListener: (resource: Resource) -> Unit
    )
}

enum class ScaleType {
    CENTER_CROP, FIT_CENTER, FIT_XY,
}