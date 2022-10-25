package com.huafang.module_login.view

import android.os.Bundle
import com.drake.spannable.movement.ClickableMovementMethod
import com.drake.spannable.replaceSpan
import com.drake.spannable.span.HighlightSpan
import com.dylanc.longan.doOnClick
import com.dylanc.longan.getCompatColor
import com.dylanc.longan.toast
import com.huafang.module_login.R
import com.huafang.module_login.databinding.LoginActivityOneKeyLoginBinding
import com.huafang.module_login.ext.showAgreementDialog
import com.huafang.mvvm.repository.UserRepository
import com.huafang.mvvm.ui.BaseBindingActivity
import com.zackratos.ultimatebarx.ultimatebarx.statusBarOnly
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yang.guo on 2022/10/17
 * @describe 一键登陆页面
 */
@AndroidEntryPoint
class OneKeyLoginActivity : BaseBindingActivity<LoginActivityOneKeyLoginBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        showAgreementDialog { agreement ->
            binding.ivAgreement.isEnabled = agreement
        }
        statusBarOnly {
            // 设置状态栏字体颜色
            light = true
            // 设置状态栏为透明色
            transparent()
        }
        binding.run {
            tvPhone.text = "150****0678"
            ivAgreement.isEnabled = UserRepository.isAgreement
            llAgreement.doOnClick {
                UserRepository.isAgreement = !UserRepository.isAgreement
                ivAgreement.isEnabled = UserRepository.isAgreement
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