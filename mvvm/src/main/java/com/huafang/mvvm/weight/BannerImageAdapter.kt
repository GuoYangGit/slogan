package com.huafang.mvvm.weight

import com.drake.brv.BindingAdapter
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
class ViewPagerImageAdapter : BindingAdapter() {
    init {
        addType<String>(R.layout.item_image_adapter)
        onBind {
            val binding = getBinding<ItemImageAdapterBinding>()
            binding.imageView.loadImage(getModel<String>())
        }
    }
}