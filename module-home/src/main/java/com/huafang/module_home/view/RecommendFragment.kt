package com.huafang.module_home.view

import android.os.Bundle
import com.dylanc.longan.viewLifecycleScope
import com.guoyang.base.ext.bindBaseAdapter
import com.guoyang.base.ext.staggered
import com.huafang.module_home.adapter.RecommendAdapter
import com.huafang.module_home.databinding.HomeFragmentRecommendBinding
import com.huafang.module_home.entity.RecommendEntity
import com.huafang.mvvm.ui.BaseBindingFragment
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

/**
 * @author yang.guo on 2022/10/14
 * @describe
 */
@AndroidEntryPoint
class RecommendFragment : BaseBindingFragment<HomeFragmentRecommendBinding>() {

    private val recommendAdapter: RecommendAdapter by lazy {
        RecommendAdapter()
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.recyclerView
            .staggered(2)
            .bindBaseAdapter(recommendAdapter)
    }

    override fun lazyLoadData() {
        viewLifecycleScope.async(Dispatchers.IO) {
            PictureSelector.create(this@RecommendFragment)
                .dataSource(SelectMimeType.ofImage())
                .obtainMediaData {
                    val list = it.map { RecommendEntity(url = it.path) }
                    recommendAdapter.setList(list)
                }
        }
    }
}