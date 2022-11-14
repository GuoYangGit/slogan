package com.huafang.module_login.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.drake.spannable.movement.ClickableMovementMethod
import com.drake.spannable.replaceSpan
import com.drake.spannable.span.HighlightSpan
import com.guoyang.utils_helper.doOnClick
import com.guoyang.utils_helper.getCompatColor
import com.guoyang.utils_helper.immersive
import com.guoyang.utils_helper.toast
import com.huafang.module_login.R
import com.huafang.module_login.databinding.LoginActivityPhoneLoginBinding
import com.huafang.mvvm.repository.UserRepository
import com.huafang.mvvm.view.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * 手机号登陆页面
 * @author yang.guo on 2022/10/17
 */
@AndroidEntryPoint
class PhoneLoginActivity : BaseBindingActivity<LoginActivityPhoneLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        binding.run {
            immersive(darkMode = true)
            changeBtn()
            ivClear.doOnClick {
                etPhone.setText("")
            }
            etPhone.doOnTextChanged { _, _, _, _ ->
                changeBtn()
            }
            btnLogin.doOnClick {
                if (!UserRepository.isAgreement) {
                    toast(getString(R.string.login_plase_agree_hint))
                    return@doOnClick
                }
                val phone = etPhone.text.toString()
                if (phone.isBlank()) return@doOnClick
                VerificationCodeActivity.start(phone)
            }

            ivAgreement.isEnabled = UserRepository.isAgreement
            llAgreement.doOnClick {
                UserRepository.isAgreement = !UserRepository.isAgreement
                ivAgreement.isEnabled = UserRepository.isAgreement
            }
            // 保证没有点击背景色
            tvAgreement.movementMethod = ClickableMovementMethod.getInstance()
            tvAgreement.text =
                getString(R.string.login_agreement_title).replaceSpan(getString(R.string.login_agreement_operator)) { matchResult ->
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

    private fun changeBtn() {
        binding.apply {
            val isEnable = etPhone.text.isNotBlank()
            btnLogin.isEnabled = isEnable
            btnLogin.alpha = if (isEnable) 1.0f else 0.3f
            ivClear.visibility = if (isEnable) View.VISIBLE else View.GONE
        }
    }
}