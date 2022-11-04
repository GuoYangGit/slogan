package com.guoyang.sdk_giftview

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.tencent.qgame.animplayer.AnimConfig
import com.tencent.qgame.animplayer.AnimView
import com.tencent.qgame.animplayer.inter.IAnimListener
import com.tencent.qgame.animplayer.inter.IFetchResource
import com.tencent.qgame.animplayer.inter.OnResourceClickListener
import com.tencent.qgame.animplayer.mix.Resource
import java.io.File

/**
 * @author yang.guo on 2022/11/4
 * @describe 礼物播放控件
 */
class GiftView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AnimView(context, attrs, defStyleAttr), IGiftView, LifecycleEventObserver {

    init {
        if (context is LifecycleOwner) {
            context.lifecycle.addObserver(this)
        }
    }

    /**
     * 进行生命周期绑定
     */
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_PAUSE -> stopPlay()
            else -> {}
        }
    }

    /**
     * 播放礼物
     * @param file 礼物本地文件
     */
    override fun startPlay(file: File) {
        super.startPlay(file)
    }

    /**
     * 播放礼物
     * @param assetManager asset管理器
     * @param assetsPath asset路径
     */
    override fun startPlay(assetManager: AssetManager, assetsPath: String) {
        super.startPlay(assetManager, assetsPath)
    }

    /**
     * 停止播放
     */
    override fun stopPlay() {
        super.stopPlay()
    }

    /**
     * 设置播放次数
     * @param count 播放次数
     */
    override fun setLoop(count: Int) {
        super.setLoop(count)
    }

    /**
     * 设置是否静音
     * @param isMute 是否静音
     */
    override fun setMute(isMute: Boolean) {
        super.setMute(isMute)
    }

    /**
     * 设置礼物播放的缩放模式
     * @param scaleType 缩放模式(默认FIT_XY)
     */
    override fun setScale(scaleType: ScaleType) {
        setScaleType(
            when (scaleType) {
                ScaleType.CENTER_CROP -> com.tencent.qgame.animplayer.util.ScaleType.CENTER_CROP
                ScaleType.FIT_CENTER -> com.tencent.qgame.animplayer.util.ScaleType.FIT_CENTER
                ScaleType.FIT_XY -> com.tencent.qgame.animplayer.util.ScaleType.FIT_XY
            }
        )
    }

    /**
     * 设置播放监听
     * @param onStart 开始播放
     * @param onEnd 结束播放
     */
    override fun addAnimListener(onStart: () -> Unit, onEnd: () -> Unit) {
        setAnimListener(object : IAnimListener {

            /**
             * 配置准备好后回调
             * @return true 继续播放 false 结束播放
             */
            override fun onVideoConfigReady(config: AnimConfig): Boolean {
                return true
            }

            /**
             * 开始播放
             */
            override fun onVideoStart() {
                onStart()
            }

            /**
             * 视频渲染每一帧时的回调
             * @param frameIndex 帧索引
             */
            override fun onVideoRender(frameIndex: Int, config: AnimConfig?) {

            }

            /**
             * 视频播放结束(失败也会回调onComplete)
             */
            override fun onVideoComplete() {
                onEnd()
            }

            /**
             * 失败回调
             * 一次播放时可能会调用多次，建议onFailed只做错误上报
             * @param errorType 错误类型
             * @param errorMsg 错误消息
             */
            override fun onFailed(errorType: Int, errorMsg: String?) {
                Log.d("GiftView", "onFailed: $errorType, $errorMsg")
            }

            /**
             * 播放器被销毁情况下会调用onVideoDestroy
             */
            override fun onVideoDestroy() {
            }

        })
    }

    /**
     * 添加融合动画
     * @param fetchText 获取文字
     * @param fetchImage 获取图片
     * @param fetchOnClickListener 获取点击事件
     */
    override fun addFetchResource(
        fetchText: (resource: Resource, result: (String?) -> Unit) -> Unit,
        fetchImage: (resource: Resource, result: (Bitmap?) -> Unit) -> Unit,
        fetchOnClickListener: (resource: Resource) -> Unit
    ) {
        this.setFetchResource(object : IFetchResource {
            /**
             * 获取图片 (暂时不支持Bitmap.Config.ALPHA_8 主要是因为一些机型opengl兼容问题)
             */
            override fun fetchImage(resource: Resource, result: (Bitmap?) -> Unit) {
                fetchImage.invoke(resource, result)
            }

            /**
             * 获取文字
             */
            override fun fetchText(resource: Resource, result: (String?) -> Unit) {
                fetchText.invoke(resource, result)
            }

            /**
             * 资源释放通知
             */
            override fun releaseResource(resources: List<Resource>) {

            }
        })
        this.setOnResourceClickListener(object : OnResourceClickListener {
            /**
             * 返回被点击的资源
             */
            override fun onClick(resource: Resource) {
                fetchOnClickListener.invoke(resource)
            }
        })
    }
}