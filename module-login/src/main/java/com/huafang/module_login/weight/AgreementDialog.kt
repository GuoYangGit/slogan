package com.huafang.module_login.weight

import android.content.Context
import com.drake.spannable.movement.ClickableMovementMethod
import com.drake.spannable.replaceSpan
import com.drake.spannable.span.HighlightSpan
import com.dylanc.longan.doOnClick
import com.dylanc.longan.getCompatColor
import com.dylanc.longan.getString
import com.dylanc.longan.toast
import com.huafang.module_login.R
import com.huafang.module_login.databinding.LoginDialogAgreementBinding
import com.lxj.xpopup.core.CenterPopupView

/**
 * @author yang.guo on 2022/10/17
 * 用户隐私协议弹窗
 */
class AgreementDialog(context: Context, private val block: (agree: Boolean) -> Unit) :
    CenterPopupView(context) {
    override fun onCreate() {
        super.onCreate()
        val binding = LoginDialogAgreementBinding.bind(popupImplView)
        binding.run {
            // 保证没有点击背景色
            tvContent.movementMethod = ClickableMovementMethod.getInstance()
            tvContent.text = getString(R.string.login_agreement_dialog_content)
                .replaceSpan(getString(R.string.login_agreement_user)) { matchResult ->
                    HighlightSpan(getCompatColor(R.color.colorPrimary)) {
                        context.toast("点击用户 ${matchResult.value}")
                    }
                }.replaceSpan(getString(R.string.login_agreement_privacy)) { matchResult ->
                    HighlightSpan(getCompatColor(R.color.colorPrimary)) {
                        context.toast("点击用户 ${matchResult.value}")
                    }
                }
            btnAgree.doOnClick {
                block(true)
                dismiss()
            }
            tvDisagree.doOnClick {
                block(false)
                dismiss()
            }
        }
    }

    override fun getImplLayoutId(): Int = R.layout.login_dialog_agreement
}