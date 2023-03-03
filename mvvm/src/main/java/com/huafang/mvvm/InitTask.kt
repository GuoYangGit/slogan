package com.huafang.mvvm

import android.content.Context
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.drake.brv.PageRefreshLayout
import com.drake.statelayout.StateConfig
import com.guoyang.utils_helper.*
import com.effective.android.anchors.task.Task
import com.effective.android.anchors.task.TaskCreator
import com.effective.android.anchors.task.project.Project
import com.github.forjrking.image.core.ImageOptions
import com.github.forjrking.image.glide.AppGlideModuleIml
import com.github.forjrking.image.glide.IAppGlideOptions
import com.guoyang.xloghelper.LogHelper
import com.guoyang.xloghelper.xLogD
import com.huafang.mvvm.repository.UserRepository
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.ssl.HttpsUtils
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLSession

// <editor-fold desc="App初始化任务ID">

const val TASK_APP_INIT = "task_app_init"
const val TASK_NET_INIT = "task_net_init"
const val TASK_AROUTER_INIT = "task_arouter_init"
const val TASK_VIEW_INIT = "task_view_init"
const val TASK_IMAGE_LOAD_INIT = "task_image_load_init"

// </editor-fold>

/**
 * 初始化App通用任务
 */
class AppInitTask : Task(TASK_APP_INIT) {
    override fun run(name: String) {
        // 初始化日志打印
        LogHelper.init(application, BuildConfig.DEBUG, LOG_PATH) {
            this.pubKey = XLOG_PUBKEY
        }
        // 全局处理未捕获的异常
        handleUncaughtException { thread, throwable ->
            if (thread.name == "FinalizerWatchdogDaemon" && throwable is TimeoutException) {
                xLogD(throwable.message)
                return@handleUncaughtException
            }
        }
    }
}

/**
 * 初始化网络请求任务
 */
class NetInitTask : Task(id = TASK_NET_INIT, isAsyncTask = true) {
    override fun run(name: String) {
        val sslParams = HttpsUtils.getSslSocketFactory()
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager) //添加信任证书
            .hostnameVerifier { _: String?, _: SSLSession? -> true } //忽略host验证
            .build()
        // 初始化网络请求
        RxHttpPlugins.init(okHttpClient)
            .setDebug(isAppDebug)
            .setOnParamAssembly { param ->
                if (param.url.contains(DUAN_ZI_LE_HTTP_BASE_URL)) {
                    // 添加公共参数
                    param.addHeader("project_token", HTTP_PROJECT_TOKEN) // 项目token
                    param.addHeader("token", UserRepository.user?.token ?: "") // 用户token
                    param.addHeader("uk", HTTP_PROJECT_TOKEN) // 用户唯一标识
                    param.addHeader("channel", HTTP_CHANNEL) // 渠道号
                    param.addHeader(
                        "app",
                        "${appVersionName};${appVersionCode};${sdkVersionCode}"
                    ) // app版本号
                    param.addHeader("device", "${deviceManufacturer};${deviceModel}") // 设备号
                }

            }
    }
}

/**
 * 初始化路由组件任务
 */
class ARouterInitTask : Task(TASK_AROUTER_INIT) {
    override fun run(name: String) {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (isAppDebug) {
            // 打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(application)
    }
}

/**
 * 初始化三方视图任务
 */
class ViewInitTask : Task(TASK_VIEW_INIT) {
    override fun run(name: String) {
        // 注册状态布局
        StateConfig.apply {
            emptyLayout = R.layout.layout_empty // 配置全局的空布局
            errorLayout = R.layout.layout_error // 配置全局的错误布局
            loadingLayout = R.layout.layout_loading // 配置全局的加载中布局
            setRetryIds(R.id.tv_empty, R.id.tv_error) // 全局的重试id
            onLoading {
                xLogD("StateConfig onLoading $it")
            }
            onEmpty {
                xLogD("StateConfig onEmpty $it")
            }
            onError {
                xLogD("StateConfig onError $it")
                if (it is Throwable) {
                    findViewById<TextView>(R.id.tv_error).text = it.message
                }
            }
            onContent {
                xLogD("StateConfig onContent $it")
            }
        }
        // 初始化刷新控件
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            ClassicsHeader(
                context
            )
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(
                context
            )
        }
        // 设置Page加载初始化Index
        PageRefreshLayout.startIndex = 0 // startIndex即index变量的初始值
    }
}

/**
 * 初始化图片加载任务
 */
class ImageLoadInitTask : Task(TASK_IMAGE_LOAD_INIT) {
    override fun run(name: String) {
        //代替AppGlideModule实现来修改glide配置接口
        AppGlideModuleIml.options = object : IAppGlideOptions {
            override fun applyOptions(context: Context, builder: GlideBuilder) {
                //修改缓存大小等
                xLogD("applyOptions")
            }

            override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
                //修改注册组件 例如 okhttp 注意如果修改可能会导致进度丢失
                xLogD("registerComponents")
            }
        }
        //配置全局占位图 错误图 非必须
        ImageOptions.DrawableOptions.setDefault {
            placeHolderResId = R.mipmap.icon_male_default
            errorResId = R.mipmap.icon_male_default
        }
    }
}

/**
 * 创建任务构建器
 */
object AppTaskCreator : TaskCreator {
    override fun createTask(taskName: String): Task {
        return when (taskName) {
            TASK_APP_INIT -> {
                AppInitTask()
            }
            TASK_NET_INIT -> {
                NetInitTask()
            }
            TASK_AROUTER_INIT -> {
                ARouterInitTask()
            }
            TASK_VIEW_INIT -> {
                ViewInitTask()
            }
            TASK_IMAGE_LOAD_INIT -> {
                ImageLoadInitTask()
            }
            else -> {
                AppInitTask()
            }
        }
    }

}

/**
 * 创建任务工厂
 */
class AppTaskFactory : Project.TaskFactory(AppTaskCreator)