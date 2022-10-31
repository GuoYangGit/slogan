package com.huafang.mvvm

import com.dylanc.longan.internalCacheDirPath

/**
 * @author yang.guo on 2022/10/31
 * @describe 全局定义常量
 */
// 日志解析公钥
const val xLogPubKey =
    "b8d7998c8fcab9a3cc541f5b738e7f9d558ac55f68384793af206633638fe3ed789e66354064f3f5e5ef7f368a5ed90496ea0bbb5e7e44ffd0f654caccd8b32d"

// 日志保存路径
val logPath by lazy { "$internalCacheDirPath/mars" }