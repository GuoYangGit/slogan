package com.huafang.mvvm.ext

import android.widget.ImageView
import androidx.annotation.ColorInt
import com.github.forjrking.image.load
import com.huafang.mvvm.R
import com.huafang.mvvm.entity.UserEntity

/**
 * @author yang.guo on 2022/10/18
 * @describe 图片加载扩展类
 */
fun ImageView.loadAvatar(
    url: String?,
    sex: Int? = UserEntity.SEX_FEMALE,
    borderWidth: Int = 0,
    @ColorInt borderColor: Int = 0,
) {
    val placeHolder =
        if (sex == UserEntity.SEX_FEMALE) R.mipmap.icon_female_default else R.mipmap.icon_male_default
    load(url) {
        this.placeHolderResId = placeHolder
        this.errorResId = placeHolder
        this.borderWidth = borderWidth
        this.borderColor = borderColor
    }
}