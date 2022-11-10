package com.huafang.mvvm.weight.textview

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


/**
 * @author yang.guo on 2022/11/7
 *
 */
internal class ExpandCollapseAnimation(target: View, startHeight: Int, endHeight: Int) :
    Animation() {
    //动画执行view
    private val mTargetView: View

    //动画执行的开始高度
    private val mStartHeight: Int

    //动画结束后的高度
    private val mEndHeight: Int

    init {
        mTargetView = target
        mStartHeight = startHeight
        mEndHeight = endHeight
        duration = 400
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        //计算出每次应该显示的高度，改变执行view的高度，实现动画
        mTargetView.layoutParams.height =
            ((mEndHeight - mStartHeight) * interpolatedTime + mStartHeight).toInt()
        mTargetView.requestLayout()
    }
}