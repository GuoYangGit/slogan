package com.huafang.mvvm.net

import androidx.annotation.Keep
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.convertTo
import java.io.IOException
import java.lang.reflect.Type

/***
 * 网络请求统一响应类
 * @author Yang.Guo on 2021/6/2.
 */
@Keep
class DuanZiLeBaseResponse<T> {
    var code = 0
    var msg: String? = null
    var data: T? = null
}

/***
 * 网络统一业务处理解析类
 * @author Yang.Guo on 2021/6/2.
 */
@Parser(name = "DuanZiLeResponse")
open class DuanZiLeResponseParser<T> : TypeParser<T> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    @Throws(IOException::class)
    override fun onParse(response: okhttp3.Response): T {
        val data: DuanZiLeBaseResponse<T> = response.convertTo(DuanZiLeBaseResponse::class, *types)
        val t = data.data     //获取data字段
        if (data.code != 200 || t == null) { //code不等于0，说明数据不正确，抛出异常
            throw ParseException(data.code.toString(), data.msg, response)
        }
        return t  //最后返回data字段
    }
}