package com.huafang.module_login.viewmodel

import com.huafang.module_login.model.LoginRepository
import com.huafang.mvvm.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    fun getLoginCode(phone: String) = loginRepository.getLoginCode(phone)

    fun login(phone: String, code: String) = loginRepository.login(phone, code)
}