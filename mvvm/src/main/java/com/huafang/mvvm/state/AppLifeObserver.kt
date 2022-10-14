package com.huafang.mvvm.state

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

/***
 * App前后台切换监听
 * @author Yang.Guo on 2021/5/31.
 */
object AppLifeObserver : LifecycleEventObserver {
    val isForeground by lazy { MutableLiveData<Boolean>() }

    /**
     * 前台展示
     */
    private fun onForeground() {
        isForeground.value = true
    }

    /**
     * 后台展示
     */
    private fun obBackground() {
        isForeground.value = false
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> onForeground()
            Lifecycle.Event.ON_STOP -> obBackground()
            else -> {}
        }
    }
}