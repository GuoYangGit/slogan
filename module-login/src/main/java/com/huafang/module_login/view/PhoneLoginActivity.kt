package com.huafang.module_login.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.drake.spannable.movement.ClickableMovementMethod
import com.drake.spannable.replaceSpan
import com.drake.spannable.span.HighlightSpan
import com.dylanc.longan.doOnClick
import com.dylanc.longan.getCompatColor
import com.dylanc.longan.startActivity
import com.dylanc.longan.toast
import com.huafang.module_login.R
import com.huafang.module_login.databinding.LoginActivityPhoneLoginBinding
import com.huafang.mvvm.repository.UserRepository
import com.huafang.mvvm.ui.BaseBindingActivity
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBarOnly

/**
 * @author yang.guo on 2022/10/17
 * @describe
 */
class PhoneLoginActivity : BaseBindingActivity<LoginActivityPhoneLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        statusBarOnly {
            // 设置状态栏字体颜色
            light = true
            // 设置状态栏为透明色
            transparent()
        }
        binding.run {
            tvTitle.addStatusBarTopPadding()
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