package com.huafang.mvvm.weight.richinput

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 话题bean
 * @author yang.guo on 2022/11/7
 */
@Parcelize
data class TopicBean(
    var topicText: String = "",// 高亮文本
    var start: Int = 0,// 高亮文本开始位置
    var end: Int = 0,// 高亮文本结束位置
) : Parcelable