package com.huafang.module_home.view.adapter

import android.view.View
import com.drake.brv.BindingAdapter
import com.drake.spannable.movement.ClickableMovementMethod
import com.drake.spannable.replaceSpan
import com.drake.spannable.span.HighlightSpan
import com.guoyang.base.ext.getDateStr
import com.guoyang.utils_helper.dp
import com.guoyang.utils_helper.getCompatColor
import com.guoyang.utils_helper.screenWidth
import com.guoyang.utils_helper.toast
import com.guoyang.xloghelper.xLogD
import com.huafang.module_home.R
import com.huafang.module_home.databinding.HomeItemContentBinding
import com.huafang.module_home.entity.ContentEntity
import com.huafang.mvvm.ext.loadAvatar
import com.huafang.mvvm.repository.UserRepository
import javax.inject.Inject

/**
 * 用户发布内容Item适配器
 * @author yang.guo on 2022/10/14
 */
class ContentAdapter @Inject constructor() : BindingAdapter() {
    init {
        addType<ContentEntity>(R.layout.home_item_content)

        onBind {
            val item = getModel<ContentEntity>()
            getBinding<HomeItemContentBinding>().apply {
                // 设置用户头像
                ivAvatar.loadAvatar(item.currentUser.avatar, item.currentUser.sex)
                // 设置用户昵称
                tvUserName.text = item.currentUser.userName
                // 设置用户城市｜发布日期
                val time = item.data.getDateStr("MM月dd日")
                val userInfo = if (item.locationName.isBlank()) {
                    time
                } else {
                    "${item.locationName} | $time"
                }
                tvUserContent.text = userInfo
                // 设置点赞用户
                if (item.likeUser.isEmpty()) {
                    peopleLikeGroup.visibility = View.GONE
                } else {
                    peopleLikeGroup.visibility = View.VISIBLE
                    tvPeopleLike.text =
                        context.getString(
                            R.string.home_people_like_number,
                            item.likeUser.size.toString()
                        )
                    peopleLikeView.setList(item.likeUser.map { it.avatar })
                }
                ivMyAvatar.loadAvatar(UserRepository.user?.avatar, UserRepository.user?.sex)
                val viewWidth: Int =
                    screenWidth - 32.dp.toInt()
                val contentSpan =
                    item.content.replaceSpan("@[^@]+?(?=\\s|\$)".toRegex()) { matchResult ->
                        HighlightSpan(tvContent.getCompatColor(R.color.colorPrimary)) {
                            xLogD("点击用户 ${matchResult.value}")
                            context.toast("点击用户 ${matchResult.value}")
                        }
                    }.replaceSpan("#[^@]+?(?=\\s|\$)".toRegex()) { matchResult ->
                        HighlightSpan(tvContent.getCompatColor(R.color.colorPrimary)) {
                            xLogD("点击标签 ${matchResult.value}")
                            context.toast("点击标签 ${matchResult.value}")
                        }
                    }
                // 保证没有点击背景色
                tvContent.movementMethod = ClickableMovementMethod.getInstance()
                tvContent.initWidth(viewWidth)
                    .setMaxLine(2)
                    .setSuffixColor(R.color.colorPrimary)
                    .setOriginalText(contentSpan)
            }
        }
    }
}