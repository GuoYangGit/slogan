package com.huafang.sdk_video

import com.shuyu.gsyvideoplayer.cache.CacheFactory
import com.shuyu.gsyvideoplayer.cache.ProxyCacheManager
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.shuyu.gsyvideoplayer.utils.GSYVideoType


class GSYVideoManager : IPlayerFactory {
    override fun initPlayer() {
        println("initPlayer")

        // 切换内核
        PlayerFactory.setPlayManager(IjkPlayerManager::class.java) //ijk模式
//        PlayerFactory.setPlayManager(Exo2PlayerManager::class.java) //EXO模式
//        PlayerFactory.setPlayManager(SystemPlayerManager::class.java) //系统模式
//        PlayerFactory.setPlayManager(AliPlayerManager::class.java) //aliplay 内核模式

        // 缓存模式
//        CacheFactory.setCacheManager(ExoPlayerCacheManager::class.java) //exo缓存模式，支持m3u8，只支持exo
        CacheFactory.setCacheManager(ProxyCacheManager::class.java) //代理缓存模式，支持所有模式，不支持m3u8等

        // 切换比例
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_DEFAULT) // 显示默认比例
//        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_16_9) // 显示16:9比例
//        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_4_3) // 显示4:3比例
//        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL) // 全屏裁减显示，为了显示正常 CoverImageView 建议使用FrameLayout作为父布局
//        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL) // 全屏拉伸显示，使用这个属性时，surface_container建议使用FrameLayout

        // 切换渲染模式
        GSYVideoType.setRenderType(GSYVideoType.TEXTURE) // 默认TextureView
//        GSYVideoType.setRenderType(GSYVideoType.SUFRACE) // SurfaceView，动画切换等时候效果比较差
//        GSYVideoType.setRenderType(GSYVideoType.GLSURFACE) // GLSurfaceView、支持滤镜
    }
}