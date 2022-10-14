package com.huafang.module_home.adapter

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dylanc.longan.dp
import com.dylanc.longan.getString
import com.dylanc.viewbinding.brvah.getBinding
import com.github.forjrking.image.loadCircleImage
import com.github.forjrking.image.loadImage
import com.guoyang.base.ext.getDateStr
import com.huafang.module_home.R
import com.huafang.module_home.databinding.HomeItemContentBinding
import com.huafang.module_home.entity.ContentEntity
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

/**
 * @author yang.guo on 2022/10/14
 * @describe 用户发布内容Item适配器
 */
class ContentAdapter(private val owner: LifecycleOwner) :
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
            banner.setAdapter(object : BannerImageAdapter<String>(item.urls) {
                override fun onBindView(
                    holder: BannerImageHolder?, data: String?, position: Int, size: Int
                ) {
                    holder?.imageView?.scaleType = ImageView.ScaleType.FIT_CENTER
                    holder?.imageView?.loadImage(data)
                }
            })
                .addBannerLifecycleObserver(owner)
                .setIndicator(viewIndicator, false)
                .setIndicatorSelectedColorRes(R.color.home_indicator_selected_color)
                .setIndicatorNormalColorRes(R.color.home_indicator_normal_color)
                .setIndicatorSpace(5.dp.toInt())
                .setIndicatorNormalWidth(4.dp.toInt())
                .setIndicatorSelectedWidth(6.dp.toInt())
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
        }
    }
}