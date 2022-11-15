package com.guoyang.sdk_giftview

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * 播放队列
 * @author yang.guo on 2022/11/8
 */
class PlayQueue<T : View>(
    private val view: T,
    private val lifecycleOwner: LifecycleOwner? = null,
    private val inactiveEvent: Lifecycle.Event = Lifecycle.Event.ON_PAUSE
) : LifecycleEventObserver {
    /**
     * 是否正在播放
     */
    @Volatile
    private var isPlaying = false

    /**
     * 播放队列
     */
    private val queue = mutableListOf<(T) -> Unit>()

    init {
        // 绑定生命周期
        if (lifecycleOwner != null) {
            lifecycleOwner.lifecycle.addObserver(this)
        } else if (view.context is LifecycleOwner) {
            (view.context as LifecycleOwner).lifecycle.addObserver(this)
        }
    }

    /**
     * 添加任务
     */
    @Synchronized
    fun addTask(task: (T) -> Unit) {
        queue.add(task)
        play()
    }

    /**
     * 通知播放结束
     */
    @Synchronized
    fun notifyPlayEnd() {
        isPlaying = false
        play()
    }

    /**
     * 清除队列
     */
    @Synchronized
    fun clearTask() {
        queue.clear()
    }

    /**
     * 播放
     */
    @Synchronized
    private fun play() {
        if (queue.isEmpty() || isPlaying) return
        isPlaying = true
        queue.removeFirst().invoke(view)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            inactiveEvent -> {
                clearTask()
            }
            else -> {}
        }
    }
}