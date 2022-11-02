package com.huafang.mvvm

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.ProcessLifecycleOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.drake.statelayout.StateConfig
import com.dylanc.longan.*
import com.effective.android.anchors.task.Task
import com.effective.android.anchors.task.TaskCreator
import com.effective.android.anchors.task.project.Project
import com.github.forjrking.image.core.ImageOptions
import com.github.forjrking.image.glide.AppGlideModuleIml
import com.github.forjrking.image.glide.IAppGlideOptions
import com.guoyang.loghelper.LogHelper
import com.guoyang.loghelper.xLogD
import com.guoyang.base.AppLifeObserver
import com.huafang.mvvm.weight.CustomLoadMoreView
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import rxhttp.wrapper.ssl.HttpsUtils
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLSession

/**
 * @author yang.guo on 2022/10/13
 * @describe 第三方Application初始化工具类
 */

const val TASK_APP_INIT = "task_app_init"
const val TASK_NET_INIT = "task_net_init"
const val TASK_AROUTER_INIT = "task_arouter_init"
const val TASK_VIEW_INIT = "task_view_init"
const val TASK_IMAGE_LOAD_INIT = "task_image_load_init"

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

class NetInitTask : Task(TASK_NET_INIT) {
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
        RxHttpPlugins.init(okHttpClient).setDebug(isAppDebug)
    }
}

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
        LoadMoreModuleConfig.defLoadMoreView = CustomLoadMoreView()
    }
}

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

class AppTaskFactory : Project.TaskFactory(AppTaskCreator)