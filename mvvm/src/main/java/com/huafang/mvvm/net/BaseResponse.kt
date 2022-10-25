package com.huafang.mvvm.net

import androidx.annotation.Keep

/***
 * 网络请求统一响应类
 * @author Yang.Guo on 2021/6/2.
 */
@Keep
class BaseResponse<T> {
    var errorCode = 0
    var errorMsg: String? = null
    var data: T? = null
}