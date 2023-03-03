package com.huafang.module_login.model

import com.huafang.module_login.entity.LoginEntity
import com.huafang.mvvm.entity.UserEntity
import com.huafang.mvvm.net.code
import com.huafang.mvvm.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import rxhttp.RxDuanZiLeHttp
import rxhttp.toFlowDuanZiLeResponse
import rxhttp.wrapper.exception.ParseException
import javax.inject.Inject

class LoginRepository @Inject constructor() {

    fun getLoginCode(phone: String): Flow<String> {
        return RxDuanZiLeHttp.postForm("/user/login/get_code")
            .add("phone", phone)
            .toFlowDuanZiLeResponse<String>()
            .catch {
                if (it is ParseException && it.errorCode == "200"){
                    emit(it.message)
                }
            }
    }

    fun login(phone: String, code: String): Flow<UserEntity> {
        return RxDuanZiLeHttp.postForm("/user/login/code")
            .add("phone", phone)
            .add("code", code)
            .toFlowDuanZiLeResponse<LoginEntity>()
            .map {
                val userEntity = UserEntity()
                userEntity.token = it.token
                userEntity.userID = it.userInfo.userId.toLong()
                userEntity.avatar = it.userInfo.avatar
                userEntity.userName = it.userInfo.nickname
                userEntity.birthday = it.userInfo.birthday
                userEntity.sex =
                    if (it.userInfo.sex == "男") UserEntity.SEX_MALE else UserEntity.SEX_FEMALE
                userEntity.phone = phone
                UserRepository.user = userEntity
                userEntity
            }
    }
}