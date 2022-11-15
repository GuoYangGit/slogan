package com.guoyang.sdk_mob.mob

import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import com.guoyang.sdk_mob.share.ShareListener
import java.util.HashMap

/**
 * Mob平台分享回调实现类
 * @author yang.guo on 2022/11/2
 */
internal class MobShareListener(private val listener: ShareListener) :
    PlatformActionListener {

    override fun onComplete(platform: Platform?, action: Int, data: HashMap<String, Any>?) {
        listener.onComplete()
    }

    override fun onError(platform: Platform?, action: Int, throwable: Throwable?) {
        listener.onError(throwable)
    }

    override fun onCancel(platform: Platform?, action: Int) {
        listener.onCancel()
    }
}