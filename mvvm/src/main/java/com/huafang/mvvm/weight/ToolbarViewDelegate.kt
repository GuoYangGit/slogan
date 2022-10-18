package com.huafang.mvvm.weight

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dylanc.loadingstateview.BaseToolbarViewDelegate
import com.dylanc.loadingstateview.ToolbarConfig
import com.dylanc.loadingstateview.NavBtnType
import com.dylanc.loadingstateview.toolbarExtras
import com.huafang.mvvm.databinding.LayoutToolbarBinding
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding

/**
 *  @author : yang.guo
 *  @date : 2022/10/11 16:11
 *  @description :
 */
var ToolbarConfig.rightTextColor: Int? by toolbarExtras()

class ToolbarViewDelegate : BaseToolbarViewDelegate() {
    private lateinit var binding: LayoutToolbarBinding

    override fun onCreateToolbar(inflater: LayoutInflater, parent: ViewGroup): View {
        binding = LayoutToolbarBinding.inflate(inflater, parent, false)
        binding.root.addStatusBarTopPadding()
        return binding.root
    }

    override fun onBindToolbar(config: ToolbarConfig) {
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