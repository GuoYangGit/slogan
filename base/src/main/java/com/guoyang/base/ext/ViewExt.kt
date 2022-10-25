package com.guoyang.base.ext

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.dylanc.longan.doOnClick

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 11:20
 *  @description : [View]相关的扩展方法
 */

/**
 * View双击拓展
 * @param block: 双击回调事件
 */
@SuppressLint("ClickableViewAccessibility")
inline fun <T : View> T.doOnDoubleClick(crossinline block: (T) -> Unit) {
    doOnClick {  }
    val gestureDetector = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            block(this@doOnDoubleClick)
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            this@doOnDoubleClick.performClick()
            return true
        }
    }
    val gestureDetectorCompat = GestureDetectorCompat(context, gestureDetector)
    this.setOnTouchListener { _, event ->
        gestureDetectorCompat.onTouchEvent(event)
        return@setOnTouchListener true
    }
}