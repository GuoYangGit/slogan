package com.huafang.mvvm.weight

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dylanc.viewbinding.brvah.getBinding
import com.github.forjrking.image.loadImage
import com.huafang.mvvm.R
import com.huafang.mvvm.databinding.ItemImageAdapterBinding
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * 轮播图通用适配器(适用于只有一张图片的情况)
 */
class BannerImageAdapter : BaseBannerAdapter<String>() {
    override fun bindData(
        holder: com.zhpan.bannerview.BaseViewHolder<String>?,
        data: String?,
        position: Int,
        pageSize: Int
    ) {
        holder?.run {
            val binding = ItemImageAdapterBinding.bind(itemView)
            binding.imageView.loadImage(data)
        }
    }

    override fun getLayoutId(viewType: Int): Int = R.layout.item_image_adapter
}

/**
 * ViewPager图片适配器
 */
class ViewPagerImageAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(
        R.layout.item_image_adapter
    ) {
    override fun convert(
        holder: BaseViewHolder,
        item: String
    ) {
        holder.getBinding(ItemImageAdapterBinding::bind).run {
            imageView.loadImage(item)
        }
    }
}