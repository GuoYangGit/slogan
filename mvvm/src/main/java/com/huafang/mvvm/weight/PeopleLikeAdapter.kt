package com.huafang.mvvm.weight

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dylanc.viewbinding.brvah.getBinding
import com.github.forjrking.image.loadCircleImage
import com.guoyang.utils_helper.getCompatColor
import com.huafang.mvvm.R
import com.huafang.mvvm.databinding.ItemPeopleLikeBinding

/**
 * 用户头像Adapter
 * @author yang.guo on 2022/10/14
 */
class PeopleLikeAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_people_like) {
    init {
        setDiffCallback(object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        })
    }

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.getBinding(ItemPeopleLikeBinding::bind).run {
            ivAvatar.loadCircleImage(item, 1, ivAvatar.getCompatColor(R.color.white))
        }
    }
}