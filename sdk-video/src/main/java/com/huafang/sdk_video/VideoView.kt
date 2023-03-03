package com.huafang.sdk_video

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.SeekBar
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer


class VideoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : StandardGSYVideoPlayer(context, attrs) {
    private var mCoverImage: ImageView? = null
    private var mCoverOriginUrl: String? = null
    private var mCoverOriginId = 0
    private var mDefaultRes = 0

    override fun init(context: Context?) {
        super.init(context)
        mCoverImage = findViewById(R.id.thumbImage)

        if (mThumbImageViewLayout != null &&
            (mCurrentState == -1 || mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_ERROR)
        ) {
            mThumbImageViewLayout.visibility = VISIBLE
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.video_layout_cover
    }


    fun loadCoverImage(url: String?, defaultRes: Int) {
        mCoverOriginUrl = url
        mDefaultRes = defaultRes
        Glide.with(context.applicationContext)
            .setDefaultRequestOptions(
                RequestOptions()
                    .frame(1000000)
                    .centerCrop()
                    .error(defaultRes)
                    .placeholder(defaultRes)
            )
            .load(url)
            .into(mCoverImage)
    }

    fun loadCoverImage(resId: Int, defaultRes: Int) {
        mCoverOriginId = resId
        mDefaultRes = defaultRes
        mCoverImage?.setImageResource(resId)
    }

    override fun startWindowFullscreen(
        context: Context?,
        actionBar: Boolean,
        statusBar: Boolean
    ): GSYBaseVideoPlayer? {
        val gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar)
        val sampleCoverVideo: VideoView? = gsyBaseVideoPlayer as? VideoView
        when {
            mCoverOriginUrl != null -> {
                sampleCoverVideo?.loadCoverImage(mCoverOriginUrl, mDefaultRes)
            }
            mCoverOriginId != 0 -> {
                sampleCoverVideo?.loadCoverImage(mCoverOriginId, mDefaultRes)
            }
        }
        return gsyBaseVideoPlayer
    }

    override fun showSmallVideo(
        size: Point?,
        actionBar: Boolean,
        statusBar: Boolean
    ): GSYBaseVideoPlayer? {
        //下面这里替换成你自己的强制转化
        val sampleCoverVideo: VideoView? =
            super.showSmallVideo(size, actionBar, statusBar) as? VideoView
        sampleCoverVideo?.mStartButton?.visibility = GONE
        sampleCoverVideo?.mStartButton = null
        return sampleCoverVideo
    }

    override fun cloneParams(from: GSYBaseVideoPlayer?, to: GSYBaseVideoPlayer?) {
        super.cloneParams(from, to)
        val sf: VideoView? = from as? VideoView
        val st: VideoView? = to as? VideoView
        st?.mShowFullAnimation = sf?.mShowFullAnimation
    }

    /******************* 下方重载方法，在播放开始不显示底部进度和按键，不需要可屏蔽  */
    private var byStartedClick = false

    override fun onClickUiToggle(e: MotionEvent?) {
        if (mIfCurrentIsFullscreen && mLockCurScreen && mNeedLockFull) {
            setViewShowState(mLockScreen, VISIBLE)
            return
        }
        byStartedClick = true
        super.onClickUiToggle(e)
    }

    override fun changeUiToNormal() {
        super.changeUiToNormal()
        byStartedClick = false
    }

    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        Debuger.printfLog("Sample changeUiToPreparingShow")
        setViewShowState(mBottomContainer, INVISIBLE)
        setViewShowState(mStartButton, INVISIBLE)
    }

    override fun changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow()
        Debuger.printfLog("Sample changeUiToPlayingBufferingShow")
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, INVISIBLE)
            setViewShowState(mStartButton, INVISIBLE)
        }
    }

    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        Debuger.printfLog("Sample changeUiToPlayingShow")
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, INVISIBLE)
            setViewShowState(mStartButton, INVISIBLE)
        }
    }

    override fun startAfterPrepared() {
        super.startAfterPrepared()
        Debuger.printfLog("Sample startAfterPrepared")
        setViewShowState(mBottomContainer, INVISIBLE)
        setViewShowState(mStartButton, INVISIBLE)
        setViewShowState(mBottomProgressBar, VISIBLE)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        byStartedClick = true
        super.onStartTrackingTouch(seekBar)
    }
}