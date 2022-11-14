package com.huafang.module_login.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.guoyang.utils_helper.*
import com.huafang.mvvm.util.GlideEngine
import com.huafang.module_login.R
import com.huafang.module_login.databinding.LoginRegisterUserInfoBinding
import com.huafang.mvvm.entity.UserEntity
import com.huafang.mvvm.ext.loadAvatar
import com.huafang.mvvm.util.ARouterNavigation
import com.huafang.mvvm.view.BaseBindingActivity
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yang.guo on 2022/10/18
 * 注册用户信息页面
 */
@AndroidEntryPoint
class RegisterUserInfoActivity : BaseBindingActivity<LoginRegisterUserInfoBinding>() {
    private var chooseSex: Int = UserEntity.SEX_FEMALE
        set(value) {
            field = value
            chooseSex(value)
        }
    private var chooseAvatar: String = "www.baidu.com"

    companion object {
        fun start() = startActivity<RegisterUserInfoActivity>()
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.run {
            immersive(darkMode = true)
            toolbarView.statusPadding()
            toolbarView.setToolbar {
                title = "完善资料"
                navIcon = R.mipmap.icon_close
            }
            ivAvatar.doOnDebouncingClick {
                PictureSelector.create(this@RegisterUserInfoActivity)
                    .openGallery(SelectMimeType.ofImage())
                    .setImageEngine(GlideEngine.createGlideEngine())
                    .forResult(object : OnResultCallbackListener<LocalMedia?> {
                        override fun onResult(result: ArrayList<LocalMedia?>?) {
                            if (result.isNullOrEmpty()) return
                            chooseAvatar = result[0]?.path ?: ""
                            ivAvatar.loadAvatar(chooseAvatar, chooseSex)
                        }

                        override fun onCancel() {}
                    })
            }
            etName.doOnTextChanged { _, _, _, _ ->
                changeBtn()
            }
            tvMale.doOnDebouncingClick {
                chooseSex = UserEntity.SEX_MALE
            }
            tvFemale.doOnDebouncingClick {
                chooseSex = UserEntity.SEX_FEMALE
            }
            btnCommit.doOnDebouncingClick{
                ARouterNavigation.toMainActivity()
            }
        }
        chooseSex = UserEntity.SEX_FEMALE
        changeBtn()
    }

    /**
     * 根据选择性别改变页面状态
     * @param sex：性别
     */
    private fun chooseSex(sex: Int) {
        if (chooseAvatar.isBlank()) binding.ivAvatar.loadAvatar(chooseAvatar, chooseSex)
        binding.tvFemale.isEnabled = sex != UserEntity.SEX_FEMALE
        binding.tvMale.isEnabled = sex != UserEntity.SEX_MALE
    }

    private fun changeBtn() {
        binding.apply {
            val isEnable = etName.text.isNotBlank()
            btnCommit.isEnabled = isEnable
            btnCommit.alpha = if (isEnable) 1.0f else 0.3f
        }
    }
}