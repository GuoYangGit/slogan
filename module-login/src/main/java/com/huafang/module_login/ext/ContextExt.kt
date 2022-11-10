package com.huafang.module_login.ext

import android.content.Context
import com.huafang.module_login.weight.AgreementDialog
import com.huafang.mvvm.repository.UserRepository
import com.lxj.xpopup.XPopup

/**
 * @author yang.guo on 2022/10/17
 * 关于[Context]扩展类
 */

/**
 * 显示用户协议/隐私弹窗
 */
fun Context.showAgreementDialog(block: (agree: Boolean) -> Unit) {
    if (UserRepository.isShowAgreement) return
    XPopup.Builder(this)
        .dismissOnTouchOutside(false)
        .dismissOnBackPressed(false)
        .asCustom(AgreementDialog(this) { agree ->
            // 改变用户弹窗标识
            UserRepository.isShowAgreement = true
            // 改变用户是否已同意协议
            UserRepository.isAgreement = agree
            block(agree)
        })
        .show()
}