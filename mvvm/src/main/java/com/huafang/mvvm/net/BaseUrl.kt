package com.huafang.mvvm.net

import com.huafang.mvvm.HTTP_BASE_URL
import rxhttp.wrapper.annotation.DefaultDomain

/***
 * 统一设置BaseUrl
 * @author Yang.Guo on 2021/6/2.
 */
object BaseUrl {
    @JvmField
    @DefaultDomain
    var BASE_URL = HTTP_BASE_URL
}