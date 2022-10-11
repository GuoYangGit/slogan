package com.guoyang.base

import android.app.Application
import androidx.multidex.MultiDex

/***
 *
 * 需要提供一个很有用的功能--在Activity/fragment中获取Application级别的ViewModel
 * 如果你不想继承BaseApp，又想获取Application级别的ViewModel功能
 * 那么你可以复制该类的代码到你的自定义Application中去，然后可以自己写获取ViewModel的拓展函数即GetViewModelExt类的getAppViewModel方法
 * @author Yang.Guo on 2021/5/31.
 */
open class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}