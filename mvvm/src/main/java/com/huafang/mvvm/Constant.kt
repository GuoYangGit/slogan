package com.huafang.mvvm

import com.guoyang.utils_helper.internalCacheDirPath

// <editor-fold desc="App常量类">

/**
 * 日志解析公钥
 */
const val XLOG_PUBKEY =
    "b8d7998c8fcab9a3cc541f5b738e7f9d558ac55f68384793af206633638fe3ed789e66354064f3f5e5ef7f368a5ed90496ea0bbb5e7e44ffd0f654caccd8b32d"

/**
 * 日志保存路径
 */
val LOG_PATH by lazy { "$internalCacheDirPath/mars" }

/**
 * 玩Android网络请求BaseUrl
 */
const val WAN_ANDROID_HTTP_BASE_URL = "https://wanandroid.com/"

/**
 * 段子乐网络请求BaseUrl
 */
const val DUAN_ZI_LE_HTTP_BASE_URL = "http://tools.cretinzp.com/jokes"

const val HTTP_PROJECT_TOKEN = "25F1E0FEE49D48A181C6A43E1D3E6F0E"

const val HTTP_CHANNEL = "cretin_open_api"

const val AES_PASSWORD = "cretinzp**273846"

// </editor-fold>