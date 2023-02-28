package com.huafang.module_home.view.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.core.text.parseAsHtml
import androidx.lifecycle.Lifecycle
import com.drake.brv.BindingAdapter
import com.huafang.module_home.R
import com.huafang.module_home.databinding.HomeItemBannerBinding
import com.huafang.module_home.databinding.HomeItemRecommendBinding
import com.huafang.module_home.entity.ArticleEntity
import com.huafang.module_home.entity.BannerEntity
import com.huafang.mvvm.util.ARouterNavigation
import com.huafang.mvvm.weight.BannerImageAdapter
import javax.inject.Inject

/**
 * 推荐列表适配器
 * @author yang.guo on 2022/10/15
 */
@SuppressLint("SetTextI18n")
class RecommendAdapter @Inject constructor(lifecycle: Lifecycle) : BindingAdapter() {
    init {
        addType<ArticleEntity>(R.layout.home_item_recommend)
        addType<List<BannerEntity>>(R.layout.home_item_banner)
        onBind {
            when (itemViewType) {
                R.layout.home_item_recommend -> {
                    getBinding<HomeItemRecommendBinding>().apply {
                        val item = getModel<ArticleEntity>()
                        tvTitle.text = item.title.parseAsHtml()
                        tvUserName.text =
                            "${item.author.ifEmpty { item.shareUser }} | ${item.niceDate}"
                        tvContent.text = item.desc.parseAsHtml()
                        tvContent.visibility =
                            if (item.desc.isEmpty()) View.GONE else View.VISIBLE
                        tvLikeNum.text = item.zan.toString()
                        ivLike.setImageResource(
                            if (item.collect) R.mipmap.icon_like else R.mipmap.icon_like_unselect
                        )
                        tvTag.text = item.superChapterName
                        container.setOnClickListener {
                            ARouterNavigation.toWebViewActivity(item.link, item.title)
                        }
                    }

                }
                R.layout.home_item_banner -> {
                    getBinding<HomeItemBannerBinding>().apply {
                        val item = getModel<List<BannerEntity>>()
                        bannerView.apply {
                            setAdapter(BannerImageAdapter())
                            setLifecycleRegistry(lifecycle)
                            setOnPageClickListener { _, position ->
                                val bean =
                                    item.getOrNull(position) ?: return@setOnPageClickListener
                                ARouterNavigation.toWebViewActivity(bean.url, bean.title)
                            }
                        }.create()
                        bannerView.refreshData(item.map { it.imagePath })
                    }

                }
            }
        }
    }
}