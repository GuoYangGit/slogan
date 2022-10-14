package com.huafang.mvvm.weight

import android.view.ViewGroup
import android.widget.ImageView
import com.youth.banner.adapter.BannerAdapter
import com.huafang.mvvm.weight.BannerImageAdapter.BannerViewHolder
import androidx.recyclerview.widget.RecyclerView
import com.github.forjrking.image.loadImage

/**
 * @author yang.guo on 2022/10/14
 * @describe 自定义布局，下面是常见的图片样式，更多实现可以看demo，可以自己随意发挥
 */
class BannerImageAdapter(urls: List<String>) : BannerAdapter<String, BannerViewHolder>(urls) {
    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder, url: String, position: Int, size: Int) {
        holder.imageView.loadImage(url)
    }

    inner class BannerViewHolder(var imageView: ImageView) : RecyclerView.ViewHolder(imageView)
}