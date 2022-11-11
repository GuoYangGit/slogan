package com.huafang.mvvm.util

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter

/**
 *  [ARouter]的工具类
 * @author yang.guo on 2022/10/13
 */
object ARouterNavigation {
    const val PATH_HOME_FRAGMENT = "/home/HomeFragment"
    const val PATH_Message_FRAGMENT = "/message/MessageFragment"
    const val PATH_Me_FRAGMENT = "/me/MeFragment"

    /**
     * 跳转首页Fragment
     */
    fun toHomeFragment(): Fragment =
        ARouter.getInstance().build(PATH_HOME_FRAGMENT).navigation() as Fragment

    /**
     * 跳转消息Fragment
     */
    fun toMessageFragment(): Fragment =
        ARouter.getInstance().build(PATH_Message_FRAGMENT).navigation() as Fragment

    /**
     * 跳转我的Fragment
     */
    fun toMeFragment(): Fragment =
        ARouter.getInstance().build(PATH_Me_FRAGMENT).navigation() as Fragment

}