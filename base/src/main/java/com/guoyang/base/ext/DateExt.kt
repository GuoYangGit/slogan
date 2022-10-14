package com.guoyang.base.ext

import java.text.SimpleDateFormat
import java.util.Date

/**
 * @author yang.guo on 2022/10/14
 * @describe 时间相关的扩展类
 */

/**
 * Long的扩展方法
 * 获取当前的格式化日期
 * @param format: 格式化类型(例如yyyy-MM-dd HH:mm:ss)
 */
fun Long.getDateStr(format: String): String {
    return try {
        var formatStr = format
        if (formatStr.isBlank()) {
            formatStr = "yyyy-MM-dd HH:mm:ss"
        }
        val date = Date(this)
        val formatter = SimpleDateFormat(formatStr)
        formatter.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}