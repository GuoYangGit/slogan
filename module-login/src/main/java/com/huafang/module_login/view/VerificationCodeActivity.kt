package com.huafang.module_login.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.style.StyleSpan
import com.drake.spannable.addSpan
import com.drake.spannable.span.ColorSpan
import com.dylanc.longan.*
import com.huafang.module_login.R
import com.huafang.module_login.databinding.LoginActivityVerificationCodeBinding
import com.huafang.mvvm.view.BaseBindingActivity
import com.kenny.separatededittext.SeparatedEditText
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBarOnly
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author yang.guo on 2022/10/17
 * @describe 验证码页面
 */
@AndroidEntryPoint
class VerificationCodeActivity : BaseBindingActivity<LoginActivityVerificationCodeBinding>() {
    private val phone: String? by intentExtras(KEY_PHONE)

    companion object {
        private const val KEY_PHONE = "key_phone"
        fun start(phone: String) = startActivity<VerificationCodeActivity>(KEY_PHONE to phone)
    }

    override fun initView(savedInstanceState: Bundle?) {
        statusBarOnly {
            // 设置状态栏字体颜色
            light = true
            // 设置状态栏为透明色
            transparent()
        }
        binding.apply {
            llTitle.addStatusBarTopPadding()
            ivBack.doOnClick { onBackPressedDispatcher.onBackPressed() }
            tvHint.text = getString(R.string.login_verification_code_hint).addSpan(
                phone ?: "",
                listOf(ColorSpan(getCompatColor(R.color.main_color)), StyleSpan(Typeface.BOLD))
            )
            tvDownTime.doOnClick {
                startCountDown()
            }
            viewPin.setTextChangedListener(object : SeparatedEditText.TextChangedListener {
                override fun textChanged(changeText: CharSequence?) {
                    val isEnable = (changeText?.length ?: 0) == 4
                    changeBtn(isEnable)
                }

                override fun textCompleted(text: CharSequence?) {
                    val isEnable = (text?.length ?: 0) == 4
                    changeBtn(isEnable)
                    toast(text)
                }
            })
            btnCommit.doOnClick {
                RegisterUserInfoActivity.start()
            }
        }
        changeBtn(false)
        startCountDown()
    }

    /**
     * 开始倒计时
     */
    private fun startCountDown(secondInFuture: Int = 60) {
        binding.tvDownTime.startCountDown(lifecycleOwner, secondInFuture, { secondUntilFinished ->
            text = "${secondUntilFinished}秒".addSpan(
                "可重新发送",
                ColorSpan(getCompatColor(R.color.content_color))
            )
            isEnabled = false
        }, {
            text = getString(R.string.login_verification_code_send)
            isEnabled = true
        })
    }

    private fun changeBtn(isEnable: Boolean) {
        binding.apply {
            btnCommit.isEnabled = isEnable
            btnCommit.alpha = if (isEnable) 1.0f else 0.3f
        }
    }
}