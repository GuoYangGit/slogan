package com.huafang.mvvm.net

import rxhttp.wrapper.annotation.DefaultDomain

/***
 * 统一设置BaseUrl
 * @author Yang.Guo on 2021/6/2.
 */
object BaseUrl {
    @JvmField
    @DefaultDomain
    var BASE_URL = "https://wanandroid.com/"
}