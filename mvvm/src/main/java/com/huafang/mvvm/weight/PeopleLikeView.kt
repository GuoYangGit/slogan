package com.huafang.mvvm.weight

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dylanc.longan.dp
import com.guoyang.base.ext.bindBaseAdapter
import com.guoyang.base.ext.divider
import com.guoyang.base.ext.linear

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
        linear(HORIZONTAL)
            .divider {
                setDivider(-4)
            }
            .bindBaseAdapter(peopleLikeAdapter)
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