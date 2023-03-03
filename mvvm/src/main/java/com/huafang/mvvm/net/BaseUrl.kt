package com.huafang.mvvm.net

import com.huafang.mvvm.DUAN_ZI_LE_HTTP_BASE_URL
import com.huafang.mvvm.WAN_ANDROID_HTTP_BASE_URL
import rxhttp.wrapper.annotation.Domain

/***
 * 统一设置BaseUrl
 * @author Yang.Guo on 2021/6/2.
 */
object BaseUrl {
    @JvmField
    @Domain(name = "WanAndroid", className = "WanAndroid")
    var WAN_ANDROID_BASE_URL = WAN_ANDROID_HTTP_BASE_URL

    @JvmField
    @Domain(name = "DuanZiLe", className = "DuanZiLe")
    var DUAN_ZI_LE_BASE_URL = DUAN_ZI_LE_HTTP_BASE_URL
}