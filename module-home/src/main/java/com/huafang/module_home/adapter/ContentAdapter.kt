package com.huafang.module_home.adapter

import android.graphics.Typeface
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.drake.spannable.movement.ClickableMovementMethod
import com.drake.spannable.replaceSpan
import com.drake.spannable.span.HighlightSpan
import com.dylanc.longan.dp
import com.dylanc.longan.getCompatColor
import com.dylanc.longan.logDebug
import com.dylanc.longan.screenWidth
import com.dylanc.viewbinding.brvah.getBinding
import com.github.forjrking.image.loadCircleImage
import com.guoyang.base.ext.getDateStr
import com.huafang.module_home.R
import com.huafang.module_home.databinding.HomeItemContentBinding
import com.huafang.module_home.entity.ContentEntity
import com.huafang.mvvm.weight.BannerImageAdapter


/**
 * @author yang.guo on 2022/10/14
 * @describe 用户发布内容Item适配器
 */
class ContentAdapter(private val lifecycleRegistry: Lifecycle) :
    BaseQuickAdapter<ContentEntity, BaseViewHolder>(R.layout.home_item_content) {
    init {
        setDiffCallback(object : DiffUtil.ItemCallback<ContentEntity>() {
            override fun areItemsTheSame(oldItem: ContentEntity, newItem: ContentEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ContentEntity,
                newItem: ContentEntity
            ): Boolean {
                return oldItem == newItem
            }

        })
    }

    override fun convert(holder: BaseViewHolder, item: ContentEntity) {
        holder.getBinding(HomeItemContentBinding::bind).run {
            // 设置用户头像
            ivAvatar.loadCircleImage(item.currentUser.avatar)
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
            // 设置用户发布动态轮播图
            banner.setAdapter(BannerImageAdapter())
                .setLifecycleRegistry(lifecycleRegistry)
                .setIndicatorView(viewIndicator)
                .setIndicatorSliderWidth(6.dp.toInt(), 8.dp.toInt())
                .setIndicatorSliderGap(4.dp.toInt())
                .create(item.urls)
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
            ivMyAvatar.loadCircleImage(item.currentUser.avatar)
            val viewWidth: Int =
                screenWidth - 32.dp.toInt()
            // 保证没有点击背景色
            tvContent.movementMethod = ClickableMovementMethod.getInstance()
            tvContent.initWidth(viewWidth)
            tvContent.maxLines = 2
            tvContent.setHasAnimation(true)
            tvContent.setCloseInNewLine(false)
            tvContent.setOpenSuffixColor(tvContent.getCompatColor(R.color.colorPrimary))
            tvContent.setCloseSuffixColor(tvContent.getCompatColor(R.color.colorPrimary))
            tvContent.setOriginalText(item.content.replaceSpan("@[^@]+?(?=\\s|\$)".toRegex()) { matchResult ->
                HighlightSpan(tvContent.getCompatColor(R.color.colorPrimary)) {
                    logDebug("点击用户 ${matchResult.value}")
                }
            }.replaceSpan("#[^@]+?(?=\\s|\$)".toRegex()) { matchResult ->
                HighlightSpan(tvContent.getCompatColor(R.color.colorPrimary)) {
                    logDebug("点击标签 ${matchResult.value}")
                }
            })
        }
    }
}