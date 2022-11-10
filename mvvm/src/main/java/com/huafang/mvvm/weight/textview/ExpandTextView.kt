package com.huafang.mvvm.weight.textview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.huafang.mvvm.R


/**
 * @author yang.guo on 2022/11/7
 * 可展开收起的TextView
 */
class ExpandTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_MAX_LINE = 3
        private const val DEFAULT_EXPAND_TEXT = "展开"
        private const val DEFAULT_COLLAPSE_TEXT = "收起"
    }

    private var originText: CharSequence = "" // 原始文案
    private var expandHeight = 0 // 展开的文本高度
    private var closeHeight = 0 // 收起的文本高度
    private var initWidth = 0 // TextView可展示宽度
    private var expandMaxLines = DEFAULT_MAX_LINE // 默认最大行数
    private val expandText: String = DEFAULT_EXPAND_TEXT // 展开文案
    private val closeText: String = DEFAULT_COLLAPSE_TEXT // 收起文案

    @ColorRes
    private var suffixColorRes: Int = R.color.colorPrimary // 展开收起文案颜色

    private var animating: Boolean = false // 是否正在执行动画

    // 展开Span
    private val expandSpan: SpannableString by lazy {
        SpannableString(expandText).apply {
            setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                expandText.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        maxLines = Int.MAX_VALUE
                        setCloseText(originText)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = ContextCompat.getColor(context, suffixColorRes)
                        ds.isUnderlineText = false
                    }
                },
                0,
                expandText.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
    }

    // 收起Span
    private val closeSpan: SpannableString by lazy {
        SpannableString(closeText).apply {
            setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                closeText.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        maxLines = expandMaxLines
                        setOriginalText(originText)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = ContextCompat.getColor(context, suffixColorRes)
                        ds.isUnderlineText = false
                    }
                },
                0,
                closeText.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
    }

    private val openAnim: ExpandCollapseAnimation by lazy {
        ExpandCollapseAnimation(this, closeHeight, expandHeight).apply {
            fillAfter = true
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    maxLines = Int.MAX_VALUE
                    setCloseText(originText)
                }

                override fun onAnimationEnd(animation: Animation) {
                    layoutParams.height = expandHeight
                    requestLayout()
                    animating = false
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
    }

    private val closeAnim: ExpandCollapseAnimation by lazy {
        ExpandCollapseAnimation(this, expandHeight, closeHeight).apply {
            fillAfter = true
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    maxLines = expandMaxLines
                    setOriginalText(originText)
                    layoutParams.height = closeHeight
                    requestLayout()
                    animating = false
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
    }

    init {
        movementMethod = LinkMovementMethod.getInstance()
    }

    /**
     * 初始化TextView的可展示宽度
     * @param width TextView的可展示宽度
     */
    fun initWidth(width: Int): ExpandTextView {
        initWidth = width
        return this
    }

    /**
     * 设置最大行数
     * @param maxLines 最大行数
     */
    fun setMaxLine(maxLines: Int): ExpandTextView {
        this.expandMaxLines = maxLines
        super.setMaxLines(maxLines)
        return this
    }

    /**
     * 设置展开/收起文案颜色
     * @param colorRes 颜色资源
     */
    fun setSuffixColor(@ColorRes colorRes: Int): ExpandTextView {
        this.suffixColorRes = colorRes
        return this
    }

    /**
     * 暴露的方法-默认设置文本方法（如果需要折叠就会默认折叠）
     * 如果有特殊的Span如话题之类的，需要处理完毕之后再调用此方法。
     */
    fun setOriginalText(text: CharSequence) {
        // 需要展开收起功能，先使用flag拦截，等测量完毕之后再setText显示真正的文本
        var appendShowAll = false
        this.originText = text
        var workingText: CharSequence = originText
        if (maxLines >= 0) {
            //创建出一个StaticLayout主要是为了计算行数
            val layout: Layout = createStaticLayout(workingText)
            //计算全部展开的文本高度
            expandHeight = layout.height + paddingTop + paddingBottom
            if (layout.lineCount > maxLines) {
                //获取一行显示字符个数，然后截取字符串数， 收起状态原始文本截取展示的部分
                workingText = originText.subSequence(0, layout.getLineEnd(maxLines - 1))
                //再对加上[展开]标签的文本进行测量
                val showText =
                    "${originText.subSequence(0, layout.getLineEnd(maxLines - 1))}...$expandSpan"
                var layout2: Layout = createStaticLayout(showText)
                // 对workingText进行-1截取，直到展示行数==最大行数，并且添加[展开]后刚好占满最后一行
                while (layout2.lineCount > maxLines) {
                    val lastSpace = workingText.length - 1
                    if (lastSpace == -1) break
                    workingText = workingText.subSequence(0, lastSpace)
                    layout2 = createStaticLayout("$workingText...$expandSpan")
                }
                //计算收起的文本高度
                closeHeight = layout2.height + paddingTop + paddingBottom
                appendShowAll = true
            }
        }
        setText(workingText)
        if (appendShowAll) {
            // 必须使用append，不能在上面使用+连接，否则会失效
            append("...")
            append(expandSpan)
        }
    }

    /**
     * 设置展开的文本展示-后面加上[收起]的文本标签
     */
    @SuppressLint("SetTextI18n")
    private fun setCloseText(text: CharSequence) {
        //创建出一个StaticLayout主要是为了计算行数
        val layout1: Layout = createStaticLayout(text)
        val layout2: Layout = createStaticLayout(text.toString() + closeText)
        //判断- 当展示全部原始内容时 如果 TEXT_CLOSE 需要换行才能显示完整，则直接将TEXT_CLOSE展示在下一行
        if (layout2.lineCount > layout1.lineCount) {
            setText("$originText\n")
        } else {
            setText(originText)
        }
        //加上[收起]的标签
        append(closeSpan)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun createStaticLayout(text: CharSequence): Layout {
        val contentWidth: Int = initWidth - paddingLeft - paddingRight
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val builder = StaticLayout.Builder.obtain(
                text, 0, text.length,
                paint, contentWidth
            )
            builder.setAlignment(Layout.Alignment.ALIGN_NORMAL)
            builder.setIncludePad(includeFontPadding)
            builder.setLineSpacing(lineSpacingExtra, lineSpacingMultiplier)
            builder.build()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            StaticLayout(
                text, paint, contentWidth, Layout.Alignment.ALIGN_NORMAL,
                lineSpacingMultiplier, lineSpacingExtra, includeFontPadding
            )
        } else {
            StaticLayout(
                text,
                paint,
                contentWidth,
                Layout.Alignment.ALIGN_NORMAL,
                getFloatField("mSpacingMult", 1f),
                getFloatField("mSpacingAdd", 0f),
                includeFontPadding
            )
        }
    }

    private fun getFloatField(fieldName: String, defaultValue: Float): Float {
        var value = defaultValue
        if (TextUtils.isEmpty(fieldName)) {
            return value
        }
        try {
            // 获取该类的所有属性值域
            val fields = this.javaClass.declaredFields
            for (field in fields) {
                if (TextUtils.equals(fieldName, field.name)) {
                    value = field.getFloat(this)
                    break
                }
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return value
    }


    /**
     * 执行展开动画
     */
    private fun executeExpandAnim() {
        if (animating) return
        animating = true
        clearAnimation()
        startAnimation(openAnim)
    }

    /**
     * 执行收起动画
     */
    private fun executeCloseAnim() {
        if (animating) return
        animating = true
        clearAnimation()
        startAnimation(closeAnim)
    }
}