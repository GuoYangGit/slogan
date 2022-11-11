package com.huafang.mvvm.weight

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.huafang.mvvm.databinding.LayoutToolbarBinding
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 标题栏视图
 * @author yang.guo on 2022/10/14
 */
var ToolbarConfig.rightTextColor: Int? by toolbarExtras()

class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutToolbarBinding

    init {
        binding = LayoutToolbarBinding.inflate(LayoutInflater.from(context), this, false)
        binding.root.addStatusBarTopPadding()
        addView(binding.root)
    }

    fun setToolbar(apply: ToolbarConfig.() -> Unit) {
        val config = ToolbarConfig()
        config.apply(apply)
        binding.apply {
            tvTitle.text = config.title
            when (config.navBtnType) {
                NavBtnType.ICON -> {
                    config.navIcon?.let { ivLeft.setImageResource(it) }
                    ivLeft.setOnClickListener(config.onNavClickListener)
                    tvLeft.visibility = View.GONE
                    ivLeft.visibility = View.VISIBLE
                }
                NavBtnType.TEXT -> {
                    tvLeft.text = config.navText
                    tvLeft.setOnClickListener(config.onNavClickListener)
                    tvLeft.visibility = View.VISIBLE
                    ivLeft.visibility = View.GONE
                }
                NavBtnType.ICON_TEXT -> {
                    config.navIcon?.let { ivLeft.setImageResource(it) }
                    tvLeft.text = config.navText
                    ivLeft.setOnClickListener(config.onNavClickListener)
                    tvLeft.setOnClickListener(config.onNavClickListener)
                    tvLeft.visibility = View.VISIBLE
                    ivLeft.visibility = View.VISIBLE
                }
                NavBtnType.NONE -> {
                    ivLeft.visibility = View.GONE
                    tvLeft.visibility = View.GONE
                }
            }

            if (config.rightText != null) {
                tvRight.text = config.rightText
                tvRight.setOnClickListener(config.onRightClickListener)
                tvRight.visibility = View.VISIBLE
                config.rightTextColor?.let { tvRight.setTextColor(it) }
            } else {
                tvRight.visibility = View.GONE
            }

            if (config.rightIcon != null) {
                ivRight.setImageResource(config.rightIcon!!)
                ivRight.setOnClickListener(config.onRightClickListener)
                ivRight.visibility = View.VISIBLE
            } else {
                ivRight.visibility = View.GONE
            }
        }
    }
}

enum class NavBtnType {
    ICON, TEXT, ICON_TEXT, NONE
}

class ToolbarConfig(
    var title: String? = null, // 标题
    var navBtnType: NavBtnType = NavBtnType.ICON, // 显示模式
    val extras: HashMap<String, Any?> = HashMap(), // 扩展属性
) {
    @DrawableRes
    // 左边图标
    var navIcon: Int? = null

    // 左边文案
    var navText: String? = null
        private set

    // 左边点击事件
    var onNavClickListener = View.OnClickListener {
        var context: Context? = it.context
        while (context is ContextWrapper) {
            if (context is Activity) {
                context.finish()
                return@OnClickListener
            }
            context = context.baseContext
        }
    }
        private set

    // 右边图标
    @DrawableRes
    var rightIcon: Int? = null
        private set

    // 右边文案
    var rightText: String? = null
        private set

    // 右边点击事件
    var onRightClickListener: View.OnClickListener? = null
        private set

    fun navIcon(@DrawableRes icon: Int? = navIcon, listener: View.OnClickListener) {
        navIcon = icon
        onNavClickListener = listener
    }

    fun navText(text: String, listener: View.OnClickListener) {
        navText = text
        onNavClickListener = listener
    }

    fun rightIcon(@DrawableRes icon: Int, listener: View.OnClickListener) {
        rightIcon = icon
        onRightClickListener = listener
    }

    fun rightText(text: String, listener: View.OnClickListener) {
        rightText = text
        onRightClickListener = listener
    }
}

fun <T> toolbarExtras() = object : ReadWriteProperty<ToolbarConfig, T?> {
    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: ToolbarConfig, property: KProperty<*>): T? =
        thisRef.extras[property.name] as? T

    override fun setValue(thisRef: ToolbarConfig, property: KProperty<*>, value: T?) {
        thisRef.extras[property.name] = value
    }
}