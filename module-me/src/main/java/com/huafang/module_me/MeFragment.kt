package com.huafang.module_me

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.guoyang.sdk_file_transfer.doOnFailed
import com.guoyang.sdk_file_transfer.doOnLoading
import com.guoyang.sdk_file_transfer.doOnSuccess
import com.guoyang.utils_helper.doOnClick
import com.guoyang.utils_helper.internalCacheDirPath
import com.guoyang.utils_helper.launchAndCollectIn
import com.guoyang.xloghelper.xLogD
import com.huafang.mvvm.util.GlideEngine
import com.huafang.module_me.databinding.MeFragmentMeBinding
import com.huafang.module_me.viewmodel.MeViewModel
import com.huafang.mvvm.view.BaseBindingFragment
import com.huafang.mvvm.util.ARouterNavigation
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import dagger.hilt.android.AndroidEntryPoint

/**
 * 我的页面
 * @author yang.guo on 2022/10/12
 */
@AndroidEntryPoint
@Route(path = ARouterNavigation.PATH_Me_FRAGMENT)
class MeFragment : BaseBindingFragment<MeFragmentMeBinding>() {
    private val viewModel: MeViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        binding.textView.doOnClick {
//            pullAvatar()
            chooseAvatar()
        }
    }

    /**
     * 选择头像
     */
    private fun chooseAvatar() {
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofImage())
            .setImageEngine(GlideEngine.createGlideEngine())
            .forResult(object : OnResultCallbackListener<LocalMedia?> {
                override fun onResult(result: ArrayList<LocalMedia?>?) {
                    result?.let {
                        if (it.isNotEmpty()) {
                            val avatarPath = it[0]?.realPath
                            xLogD("avatarPath = $avatarPath")
                            viewModel.pushAvatar(avatarPath ?: "")
                                .launchAndCollectIn(viewLifecycleOwner) {
                                    it.doOnLoading { iTransfer, progress ->
                                        xLogD("OnLoading:progress = $progress")
                                    }.doOnSuccess { iTransfer, fileUrl, filePath ->
                                        xLogD("OnSuccess:fileUrl = $fileUrl, filePath = $filePath")
                                    }.doOnFailed {
                                        xLogD("OnFailed")
                                    }
                                }
                        }
                    }
                }

                override fun onCancel() {}
            })
    }

    private fun pullAvatar() {
        val avatarPath = "$internalCacheDirPath/avatar/avatar.jpg"
        viewModel.pullAvatar(avatarPath)
    }
}