package com.huafang.mvvm.weight

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dylanc.loadingstateview.BaseToolbarViewDelegate
import com.dylanc.loadingstateview.ToolbarConfig
import com.dylanc.loadingstateview.NavBtnType
import com.huafang.mvvm.R

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 16:11
 *  @description :
 */
class ToolbarViewDelegate : BaseToolbarViewDelegate() {
    private lateinit var tvTitle: TextView
    private lateinit var ivLeft: ImageView
    private lateinit var ivRight: ImageView

    override fun onCreateToolbar(inflater: LayoutInflater, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.layout_toolbar, parent, false)
        tvTitle = view.findViewById(R.id.tv_title)
        ivLeft = view.findViewById(R.id.iv_left)
        ivRight = view.findViewById(R.id.iv_right)
        return view
    }

    override fun onBindToolbar(config: ToolbarConfig) {
        tvTitle.text = config.title

        if (config.navBtnType == NavBtnType.NONE) {
            ivLeft.visibility = View.GONE
        } else {
            ivLeft.setOnClickListener(config.onNavClickListener)
            ivLeft.visibility = View.VISIBLE
        }

        if (config.rightIcon != null) {
            ivRight.setImageResource(config.rightIcon!!)
            ivRight.setOnClickListener(config.onRightClickListener)
            ivRight.visibility = View.VISIBLE
        }
    }
}