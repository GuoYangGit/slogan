package com.huafang.module_me

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.dylanc.longan.doOnClick
import com.guoyang.base.util.GlideEngine
import com.huafang.module_me.databinding.MeFragmentMeBinding
import com.huafang.mvvm.view.BaseBindingFragment
import com.huafang.mvvm.util.ARouterUtils
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yang.guo on 2022/10/12
 * @describe 我的页面
 */
@AndroidEntryPoint
@Route(path = ARouterUtils.PATH_Me_FRAGMENT)
class MeFragment : BaseBindingFragment<MeFragmentMeBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        binding.textView.doOnClick {
            // 选择图片
            PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia?> {
                    override fun onResult(result: ArrayList<LocalMedia?>?) {}
                    override fun onCancel() {}
                })
        }
    }
}