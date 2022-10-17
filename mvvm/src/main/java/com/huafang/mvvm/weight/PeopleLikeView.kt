package com.huafang.mvvm.weight

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dylanc.longan.dp
import com.guoyang.base.weight.decoration.SpaceItemDecoration

/**
 * @author yang.guo on 2022/10/14
 * @describe
 */
class PeopleLikeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    RecyclerView(context, attrs, defStyleAttr) {
    private val peopleLikeAdapter by lazy {
        PeopleLikeAdapter()
    }

    init {
        adapter = peopleLikeAdapter
        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        addItemDecoration(SpaceItemDecoration(-4.dp.toInt(), 0))
    }

    fun setList(urls: List<String>) {
        peopleLikeAdapter.setList(urls)
    }

    fun addData(url: String, isTop: Boolean = true) {
        if (isTop) {
            peopleLikeAdapter.addData(0, url)
        } else {
            peopleLikeAdapter.addData(url)
        }
    }
}