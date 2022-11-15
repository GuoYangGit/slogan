package com.guoyang.sdk_mob.mob

import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import com.guoyang.sdk_mob.login.LoginData
import com.guoyang.sdk_mob.login.LoginListener
import java.util.HashMap

/**
 * Mob登录回调实现类
 * @author yang.guo on 2022/11/2
 */
internal class MobLoginListener(private val listener: LoginListener) : PlatformActionListener {
    override fun onComplete(platform: Platform?, code: Int, data: HashMap<String, Any>?) {
        platform?.db?.apply {
            val loginData = LoginData(userId, userName, userIcon, userGender, exportData())
            listener.onComplete(loginData)
        } ?: listener.onError(null)
    }

    override fun onError(platform: Platform?, code: Int, throwable: Throwable?) {
        listener.onError(throwable)
    }

    override fun onCancel(platform: Platform?, code: Int) {
        listener.onCancel()
    }
}