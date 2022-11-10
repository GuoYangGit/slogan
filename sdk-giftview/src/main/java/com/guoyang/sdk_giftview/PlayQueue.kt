package com.guoyang.sdk_giftview

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @author yang.guo on 2022/11/8
 * 播放队列
 */
class PlayQueue<T : View>(
    private val view: T,
    private val lifecycleOwner: LifecycleOwner? = null,
    private val inactiveEvent: Lifecycle.Event = Lifecycle.Event.ON_PAUSE
) : LifecycleEventObserver {
    private var isPlaying = false
    private val queue = mutableListOf<(T) -> Unit>()

    init {
        if (lifecycleOwner != null) {
            lifecycleOwner.lifecycle.addObserver(this)
        } else if (view.context is LifecycleOwner) {
            (view.context as LifecycleOwner).lifecycle.addObserver(this)
        }
    }

    /**
     * 添加任务
     */
    fun addTask(task: (T) -> Unit) {
        queue.add(task)
        play()
    }

    /**
     * 通知播放结束
     */
    fun notifyPlayEnd() {
        isPlaying = false
        play()
    }

    /**
     * 播放
     */
    private fun play() {
        if (queue.isEmpty() || isPlaying) return
        isPlaying = true
        queue.removeFirst().invoke(view)
    }

    /**
     * 清除队列
     */
    private fun clear() {
        queue.clear()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            inactiveEvent -> clear()
            else -> {}
        }
    }
}