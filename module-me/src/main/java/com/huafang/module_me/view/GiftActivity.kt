package com.huafang.module_me.view

import android.graphics.BitmapFactory
import android.os.Bundle
import com.guoyang.sdk_giftview.GiftView
import com.guoyang.sdk_giftview.PlayQueue
import com.guoyang.utils_helper.doOnClick
import com.guoyang.utils_helper.mainThread
import com.guoyang.utils_helper.toast
import com.huafang.module_me.R
import com.huafang.module_me.databinding.MeActivityGiftBinding
import com.huafang.mvvm.view.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**
 * 礼物播放页面
 */
@AndroidEntryPoint
class GiftActivity : BaseBindingActivity<MeActivityGiftBinding>() {
    private lateinit var playQueue: PlayQueue<GiftView>

    override fun initView(savedInstanceState: Bundle?) {
        playQueue = PlayQueue(binding.giftView, this)
        binding.apply {
            giftView.addFetchResource({ resource, result ->
                /**
                 * tag是素材中的一个标记，在制作素材时定义解析时由业务读取tag决定需要播放的内容是什么
                 * 比如：一个素材里需要显示多个头像，则需要定义多个不同的tag，表示不同位置，需要显示不同的头像，文字类似
                 */
                if (resource.tag == "[sTxt1]") { // 此tag是已经写入到动画配置中的tag
                    val str = "恭喜 No.${1000 + Random().nextInt(8999)}用户 升神"
                    result(str)
                } else {
                    result(null)
                }
            }, { resource, result ->
                if (resource.tag == "[sImg1]") { // 此tag是已经写入到动画配置中的tag
                    val drawableId = R.mipmap.icon_male_default
                    val options = BitmapFactory.Options()
                    options.inScaled = false
                    result(BitmapFactory.decodeResource(resources, drawableId, options))
                } else {
                    result(null)
                }
            }, { resources ->
                resources.forEach {
                    it.bitmap?.recycle()
                }
            }).setOnResourceClickListener { resource ->
                toast("srcTag=${resource.tag} onClick ${resource.curPoint}")
            }.addAnimListener {
                playQueue.notifyPlayEnd()
            }
            btnGift.doOnClick {
                giftView.startPlay(this@GiftActivity.assets, "vapx.mp4")
            }
            btnQueue.doOnClick {
                for (i in 0..3) {
                    playQueue.addTask {
                        mainThread(100){
                            giftView.startPlay(this@GiftActivity.assets, "vapx.mp4")
                        }
                    }
                }
            }
            btnStop.doOnClick {
                giftView.stopPlay()
            }

        }
    }
}