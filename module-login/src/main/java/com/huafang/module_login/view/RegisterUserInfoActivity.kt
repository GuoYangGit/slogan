package com.huafang.module_login.view

import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.dylanc.longan.doOnClick
import com.dylanc.longan.startActivity
import com.guoyang.base.util.GlideEngine
import com.huafang.module_login.R
import com.huafang.module_login.databinding.LoginRegisterUserInfoBinding
import com.huafang.mvvm.entity.UserEntity
import com.huafang.mvvm.ext.loadAvatar
import com.huafang.mvvm.ui.BaseBindingActivity
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.zackratos.ultimatebarx.ultimatebarx.statusBarOnly
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yang.guo on 2022/10/18
 * @describe 注册用户信息页面
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
        statusBarOnly {
            // 设置状态栏字体颜色
            light = true
            // 设置状态栏为透明色
            transparent()
        }
        setToolbar {
            title = "完善资料"
            navIcon = R.mipmap.icon_close
        }
        binding.run {
            ivAvatar.doOnClick {
                PictureSelector.create(this@RegisterUserInfoActivity)
                    .openGallery(SelectMimeType.ofImage())
                    .setImageEngine(GlideEngine.createGlideEngine())
                    .forResult(object : OnResultCallbackListener<LocalMedia?> {
                        override fun onResult(result: ArrayList<LocalMedia?>?) {}
                        override fun onCancel() {}
                    })
            }
            etName.doOnTextChanged { text, _, _, _ ->
                val isEnabled = !text.isNullOrBlank()
                btnCommit.isEnabled = isEnabled
                btnCommit.alpha = if (isEnabled) 1.0f else 0.3f
            }
            tvMale.doOnClick {
                chooseSex = UserEntity.SEX_MALE
            }
            tvFemale.doOnClick {
                chooseSex = UserEntity.SEX_FEMALE
            }
        }
        chooseSex = UserEntity.SEX_FEMALE
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
}