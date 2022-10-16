package com.huafang.mvvm.weight

import com.github.forjrking.image.loadImage
import com.huafang.mvvm.R
import com.huafang.mvvm.databinding.ItemImageAdapterBinding
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

/**
 * @author yang.guo on 2022/10/14
 * @describe 自定义布局，下面是常见的图片样式，更多实现可以看demo，可以自己随意发挥
 */
class BannerImageAdapter : BaseBannerAdapter<String>() {
    override fun bindData(
        holder: BaseViewHolder<String>?,
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