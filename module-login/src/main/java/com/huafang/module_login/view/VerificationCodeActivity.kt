package com.huafang.module_login.view

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.style.StyleSpan
import com.drake.interval.Interval
import com.drake.spannable.addSpan
import com.drake.spannable.span.ColorSpan
import com.guoyang.utils_helper.*
import com.huafang.module_login.R
import com.huafang.module_login.databinding.LoginActivityVerificationCodeBinding
import com.huafang.mvvm.view.BaseBindingActivity
import com.kenny.separatededittext.SeparatedEditText
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

/**
 * @author yang.guo on 2022/10/17
 * 验证码页面
 */
@AndroidEntryPoint
class VerificationCodeActivity : BaseBindingActivity<LoginActivityVerificationCodeBinding>() {
    private val phone: String by bundle("", KEY_PHONE)

    companion object {
        private const val KEY_PHONE = "key_phone"
        fun start(phone: String) = startActivity<VerificationCodeActivity>(KEY_PHONE to phone)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.apply {
            immersive(darkMode = true)
            llTitle.statusPadding()
            ivBack.doOnClick { onBackPressedDispatcher.onBackPressed() }
            tvHint.text = getString(R.string.login_verification_code_hint).addSpan(
                phone,
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
                    RegisterUserInfoActivity.start()
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
    @SuppressLint("SetTextI18n")
    private fun startCountDown(secondInFuture: Long = 60) {
        Interval(secondInFuture, 1, TimeUnit.SECONDS)
            .life(this)
            .subscribe {
                binding.tvDownTime.apply {
                    text = "${secondInFuture - it}秒".addSpan(
                        "可重新发送",
                        ColorSpan(getCompatColor(R.color.content_color))
                    )
                    isEnabled = false
                }
            }.finish {
                binding.tvDownTime.apply {
                    text = getString(R.string.login_verification_code_send)
                    isEnabled = true
                }
            }.start()
    }

    private fun changeBtn(isEnable: Boolean) {
        binding.apply {
            btnCommit.isEnabled = isEnable
            btnCommit.alpha = if (isEnable) 1.0f else 0.3f
        }
    }
}