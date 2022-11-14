package com.huafang.mvvm.weight

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.BindingAdapter
import com.drake.brv.utils.setup
import com.github.forjrking.image.loadCircleImage
import com.guoyang.base.ext.divider
import com.guoyang.base.ext.linear
import com.guoyang.utils_helper.getCompatColor
import com.huafang.mvvm.R
import com.huafang.mvvm.databinding.ItemPeopleLikeBinding

/**
 * 用户点赞自定义View
 * @author yang.guo on 2022/10/14
 */
class PeopleLikeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private var peopleLikeAdapter: BindingAdapter? = null

    init {
        linear(HORIZONTAL)
            .divider {
                setDivider(-4)
            }.setup {
                addType<String>(R.layout.item_people_like)
                onBind {
                    getBinding<ItemPeopleLikeBinding>().apply {
                        ivAvatar.loadCircleImage(
                            getModel<String>(),
                            1,
                            ivAvatar.getCompatColor(R.color.white)
                        )
                    }
                }
            }.apply {
                peopleLikeAdapter = this
            }
    }

    fun setList(urls: List<String>) {
        peopleLikeAdapter?.models = urls
    }

    fun addData(url: String, isTop: Boolean = true) {
        peopleLikeAdapter?.apply {
            if (isTop) {
                mutable.add(0, url)
                notifyItemInserted(0)
            } else {
                mutable.add(url)
                notifyItemInserted(mutable.size - 1)
            }
        }
    }
}