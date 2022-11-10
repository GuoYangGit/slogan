package com.huafang.mvvm.weight

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dylanc.viewbinding.brvah.getBinding
import com.github.forjrking.image.loadImage
import com.huafang.mvvm.R
import com.huafang.mvvm.databinding.ItemImageAdapterBinding
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * @author yang.guo on 2022/10/14
 * 自定义布局，下面是常见的图片样式，更多实现可以看demo，可以自己随意发挥
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