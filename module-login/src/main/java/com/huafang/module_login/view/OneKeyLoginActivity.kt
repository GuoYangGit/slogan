package com.huafang.module_login.view

import android.os.Bundle
import com.drake.spannable.movement.ClickableMovementMethod
import com.drake.spannable.replaceSpan
import com.drake.spannable.span.HighlightSpan
import com.guoyang.utils_helper.*
import com.huafang.module_login.R
import com.huafang.module_login.databinding.LoginActivityOneKeyLoginBinding
import com.huafang.module_login.ext.showAgreementDialog
import com.huafang.mvvm.repository.UserRepository
import com.huafang.mvvm.view.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * 一键登陆页面
 * @author yang.guo on 2022/10/17
 */
@AndroidEntryPoint
class OneKeyLoginActivity : BaseBindingActivity<LoginActivityOneKeyLoginBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        // 显示用户协议弹窗
        showAgreementDialog { agreement ->
            binding.ivAgreement.isEnabled = agreement
        }
        binding.run {
            immersive(darkMode = true)
            tvPhone.text = "150****0678"
            ivAgreement.isEnabled = UserRepository.isAgreement
            llAgreement.doOnClick {
                UserRepository.isAgreement = !UserRepository.isAgreement
                ivAgreement.isEnabled = UserRepository.isAgreement
            }
            tvOtherLogin.doOnClick {
                startActivity<PhoneLoginActivity>()
            }
            // 保证没有点击背景色
            tvAgreement.movementMethod = ClickableMovementMethod.getInstance()
            tvAgreement.text =
                getString(R.string.login_agreement_title)
                    .replaceSpan(getString(R.string.login_agreement_operator)) { matchResult ->
                        HighlightSpan(getCompatColor(R.color.colorPrimary)) {
                            toast("点击用户 ${matchResult.value}")
                        }
                    }.replaceSpan(getString(R.string.login_agreement_user)) { matchResult ->
                        HighlightSpan(getCompatColor(R.color.colorPrimary)) {
                            toast("点击用户 ${matchResult.value}")
                        }
                    }.replaceSpan(getString(R.string.login_agreement_privacy)) { matchResult ->
                        HighlightSpan(getCompatColor(R.color.colorPrimary)) {
                            toast("点击用户 ${matchResult.value}")
                        }
                    }
        }
    }
}