package com.huafang.module_home.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.drake.spannable.replaceSpan
import com.drake.spannable.span.HighlightSpan
import com.dylanc.viewbinding.brvah.getBinding
import com.github.forjrking.image.loadRoundCornerImage
import com.guoyang.utils_helper.dp
import com.guoyang.utils_helper.getCompatColor
import com.guoyang.xloghelper.xLogD
import com.huafang.module_home.R
import com.huafang.module_home.databinding.HomeItemRecommendBinding
import com.huafang.module_home.entity.RecommendEntity
import com.huafang.mvvm.ext.loadAvatar
import javax.inject.Inject


/**
 * 推荐列表适配器
 * @author yang.guo on 2022/10/15
 */
class RecommendAdapter @Inject constructor() :
    BaseQuickAdapter<RecommendEntity, BaseViewHolder>(R.layout.home_item_recommend),
    LoadMoreModule {
    init {
        setDiffCallback(object : DiffUtil.ItemCallback<RecommendEntity>() {
            override fun areItemsTheSame(
                oldItem: RecommendEntity,
                newItem: RecommendEntity
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: RecommendEntity,
                newItem: RecommendEntity
            ): Boolean = oldItem == newItem

        })
    }

    override fun convert(holder: BaseViewHolder, item: RecommendEntity) {
        holder.getBinding(HomeItemRecommendBinding::bind).run {
            val layoutParams = ivImage.layoutParams
            layoutParams.height = item.imageHeight
            ivImage.layoutParams = layoutParams
            ivImage.loadRoundCornerImage(item.url, 6.dp.toInt())
            tvContent.text =
                item.content.replaceSpan("@[^@]+?(?=\\s|\$)".toRegex()) { matchResult ->
                    HighlightSpan(tvContent.getCompatColor(R.color.colorPrimary)) {
                        xLogD("点击用户 ${matchResult.value}")
                    }
                }.replaceSpan("#[^@]+?(?=\\s|\$)".toRegex()) { matchResult ->
                    HighlightSpan(tvContent.getCompatColor(R.color.colorPrimary)) {
                        xLogD("点击标签 ${matchResult.value}")
                    }
                }
            ivAvatar.loadAvatar(item.userEntity.avatar, item.userEntity.sex)
            tvUserName.text = item.userEntity.userName
            tvLikeNum.text = item.likeNun.toString()
        }
    }

}