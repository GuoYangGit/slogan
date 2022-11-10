package com.huafang.mvvm.weight.richinput

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.text.getSpans
import android.util.AttributeSet
import com.drake.spannable.listener.ModifyTextWatcher
import com.drake.spannable.replaceSpan
import com.drake.spannable.span.HighlightSpan
import com.guoyang.loghelper.xLogD

/**
 * @author yang.guo on 2022/11/7
 * 富文本输入框,支持话题|@高亮
 */
class RichInputTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {
    // 匹配规则
    private val matchRules = mapOf<Regex, (MatchResult) -> Any?>(
        "@[^@]+?(?=\\s|\$)".toRegex() to { HighlightSpan("#ed6a2c") },
        "#[^@]+?(?=\\s|\$)".toRegex() to {
            HighlightSpan(
                "#4a70d2",
                Typeface.defaultFromStyle(Typeface.BOLD)
            )
        }
    )

    // 记录上一次的长度
    private var preTextLength = 0

    // 话题集合
    private val mTopicList: MutableList<TopicBean> = mutableListOf()

    // 话题回调
    private var mListener: OnTopicEnterListener? = null

    /**
     * 设置话题回调
     */
    fun setOnTopicEnterListener(listener: OnTopicEnterListener?) {
        mListener = listener
    }

    init {
        isFocusableInTouchMode = true // 设置可触摸
        addTextChangedListener(object : ModifyTextWatcher() {
            override fun onModify(s: Editable) {
                //赋值话题列表数据
                mTopicList.clear()
                //刷新页面
                s.getSpans<HighlightSpan>().forEach {
                    s.removeSpan(it)
                }
                matchRules.forEach { rule ->
                    s.replaceSpan(rule.key) {
                        val bean = TopicBean()
                        bean.start = it.range.first
                        bean.end = it.range.last + 1
                        bean.topicText = s.toString().substring(bean.start, bean.end).trim()
                        mTopicList.add(bean)
                    }
                }
                //先添加话题再处理删除逻辑,只是判断删除[#|@]号
                if (s.length < preTextLength) {
                    val selectionStart = selectionStart
                    val selectionEnd = selectionEnd
                    // 如果光标起始和结束不在同一位置,删除文本
                    if (selectionStart != selectionEnd) {
                        // 查询文本是否属于话题对象,若是移除列表数据
                        val targetText = text.toString().substring(selectionStart, selectionEnd)
                        for (i in mTopicList.indices) {
                            val topicBean = mTopicList[i]
                            if (targetText == topicBean.topicText) {
                                mTopicList.remove(topicBean)
                                break
                            }
                        }
                    }
                    var lastPos = 0
                    // 遍历判断光标的位置
                    for (i in mTopicList.indices) {
                        val objectText = mTopicList[i].topicText
                        lastPos = text.toString().indexOf(objectText, lastPos)
                        if (lastPos != -1) {
                            if (selectionStart != 0 && selectionStart >= lastPos && selectionStart <= lastPos + objectText.length) {
                                // 选中话题
                                setSelection(lastPos, lastPos + objectText.length)
                                break
                            }
                            lastPos += objectText.length
                        }
                    }
                }
                //记录上一次的长度
                preTextLength = s.length
                matchRules.forEach { rule ->
                    s.replaceSpan(rule.key) {
                        xLogD("匹配到的文本: ${it.value}")
                        rule.value(it)
                    }
                }
            }
        })
    }

    /**
     * 替换其中的一个话题
     * @param topic 话题
     * @param position 话题在集合中的位置
     */
    fun setReplaceTopic(topic: String, position: Int) {
        if (text.isNullOrEmpty()) return
        // 原先内容
        val editable = text
        //找到老数据
        val tObject = mTopicList[position]
        val start = tObject.start
        val end = tObject.end
        // 光标位置
        val selectionStart = selectionStart
        //重新设置焦点之后会自动赋值内存的，不需要手动赋值了
        if (selectionStart >= 0 && start >= 0 && end > start) {
            //随意插入
            editable?.replace(start, end, "$topic ")
            // 移动光标到添加的内容后面
            setSelection(getSelectionStart())
        }
    }


    /**
     * 监听光标的位置,若光标处于话题内容中间则移动光标到话题结束位置
     */
    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        // TODO 这块儿的崩溃有点莫名其妙,暂时先不管了
        if (mTopicList == null) return
        if (mTopicList.isEmpty()) {
            mListener?.onTopicRemove()
            return
        }
        var startPosition = 0
        var endPosition: Int
        var objectText: String
        for (i in mTopicList.indices) {
            objectText = mTopicList[i].topicText
            while (true) {
                // 获取话题文本开始下标
                startPosition = text.toString().indexOf(objectText, startPosition)
                endPosition = startPosition + objectText.length
                if (startPosition == -1) break
                if (selStart in (startPosition + 1)..endPosition) {
                    // 若光标处于话题内容中间则移动光标到话题结束位置
                    setSelection(endPosition)
                    //回调出去并调用搜索展示下拉框选择
                    mListener?.onTopicEnter(objectText, i)
                    break
                } else {
                    mListener?.onTopicRemove()
                }
                startPosition = endPosition
            }
        }
    }

    interface OnTopicEnterListener {
        fun onTopicEnter(topic: String?, position: Int)
        fun onTopicRemove()
    }
}