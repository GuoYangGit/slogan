package com.huafang.module_webview

import android.os.Bundle
import android.widget.FrameLayout.LayoutParams
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.guoyang.utils_helper.bundle
import com.guoyang.utils_helper.immersive
import com.guoyang.utils_helper.statusPadding
import com.huafang.module_webview.databinding.WebviewActivityWebViewBinding
import com.huafang.mvvm.util.ARouterNavigation
import com.huafang.mvvm.view.BaseBindingActivity
import com.just.agentweb.AgentWeb

@Route(path = ARouterNavigation.PATH_WEBVIEW_ACTIVITY)
class WebViewActivity : BaseBindingActivity<WebviewActivityWebViewBinding>() {
    private var url: String? by bundle()
    private var title: String? by bundle()

    private var mAgentWeb: AgentWeb? = null
    override fun initView(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        binding.apply {
            immersive(darkMode = true)
            toolbar.statusPadding()
            toolbar.setToolbar {
                title = this@WebViewActivity.title
                navIcon = R.mipmap.icon_back
            }
        }
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.container, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(url)
    }
}