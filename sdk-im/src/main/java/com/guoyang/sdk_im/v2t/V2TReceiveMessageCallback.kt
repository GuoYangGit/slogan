package com.guoyang.sdk_im.v2t

import android.util.Log
import com.guoyang.sdk_im.message.IIMReceiveMessageCallback
import com.tencent.imsdk.v2.*
import java.io.File

/**
 * @author yang.guo on 2022/11/8
 * 接受消息监听抽象类
 */
class V2TReceiveMessageCallback(private val callback: IIMReceiveMessageCallback) :
    V2TIMAdvancedMsgListener() {
    /**
     * 收到新消息
     */
    override fun onRecvNewMessage(msg: V2TIMMessage?) {
        super.onRecvNewMessage(msg)
        if (msg == null) return
        callback.onReceiveMessage(msg)
        // 解析出 groupID 和 userID
        // 判断当前是单聊还是群聊
        // 如果 groupID 不为空，表示此消息为群聊；如果 userID 不为空，表示此消息为单聊
        val groupID = msg.groupID
        val userID = msg.userID
        when (msg.elemType) {
            V2TIMMessage.V2TIM_ELEM_TYPE_TEXT -> {
                // 解析出 msg 中的文本消息
                val textElem = msg.textElem
                val text = textElem.text
                Log.i("onRecvNewMessage", "text:$text")
            }
            V2TIMMessage.V2TIM_ELEM_TYPE_CUSTOM -> {
                // 解析出 msg 中的自定义消息
                val customElem = msg.customElem
                val data = customElem.data.toString()
                Log.i("onRecvNewMessage", "customData:$data")
            }
            V2TIMMessage.V2TIM_ELEM_TYPE_IMAGE -> {
                // 图片消息
                val v2TIMImageElem = msg.imageElem
                // 一个图片消息会包含三种格式大小的图片，分别为原图、大图、微缩图（SDK 会在发送图片消息的时候自动生成微缩图、大图，客户不需要关心）
                // 大图：是将原图等比压缩，压缩后宽、高中较小的一个等于720像素。
                // 缩略图：是将原图等比压缩，压缩后宽、高中较小的一个等于198像素。
                val imageList = v2TIMImageElem.imageList
                for (v2TIMImage in imageList) {
                    // 图片 ID，内部标识，可用于外部缓存 key
                    val uuid = v2TIMImage.uuid
                    // 图片类型
                    val imageType = v2TIMImage.type
                    // 图片大小（字节）
                    val size = v2TIMImage.size
                    // 图片宽度
                    val width = v2TIMImage.width
                    // 图片高度
                    val height = v2TIMImage.height
                    // 设置图片下载路径 imagePath，这里可以用 uuid 作为标识，避免重复下载
                    val imagePath = "/sdcard/im/image/myUserID$uuid"
                    val imageFile = File(imagePath)
                    // 判断 imagePath 下有没有已经下载过的图片文件
                    if (!imageFile.exists()) {
                        // 下载图片
                        v2TIMImage.downloadImage(imagePath, object : V2TIMDownloadCallback {
                            override fun onProgress(progressInfo: V2TIMElem.V2ProgressInfo) {
                                // 下载进度回调：已下载大小 v2ProgressInfo.getCurrentSize()；总文件大小 v2ProgressInfo.getTotalSize()
                            }

                            override fun onError(code: Int, desc: String) {
                                // 下载失败
                            }

                            override fun onSuccess() {
                                // 下载完成
                            }
                        })
                    } else {
                        // 图片已存在
                    }
                }
            }
            V2TIMMessage.V2TIM_ELEM_TYPE_VIDEO -> {
                // 视频消息
                val v2TIMVideoElem = msg.videoElem
                // 视频截图 ID,内部标识，可用于外部缓存 key
                val snapshotUUID = v2TIMVideoElem.snapshotUUID
                // 视频截图文件大小
                val snapshotSize = v2TIMVideoElem.snapshotSize
                // 视频截图宽
                val snapshotWidth = v2TIMVideoElem.snapshotWidth
                // 视频截图高
                val snapshotHeight = v2TIMVideoElem.snapshotHeight
                // 视频 ID,内部标识，可用于外部缓存 key
                val videoUUID = v2TIMVideoElem.videoUUID
                // 视频文件大小
                val videoSize = v2TIMVideoElem.videoSize
                // 视频时长
                val duration = v2TIMVideoElem.duration
                // 设置视频截图文件路径，这里可以用 uuid 作为标识，避免重复下载
                val snapshotPath = "/sdcard/im/snapshot/myUserID$snapshotUUID"
                val snapshotFile = File(snapshotPath)
                if (!snapshotFile.exists()) {
                    v2TIMVideoElem.downloadSnapshot(snapshotPath, object : V2TIMDownloadCallback {
                        override fun onProgress(progressInfo: V2TIMElem.V2ProgressInfo) {
                            // 下载进度回调：已下载大小 v2ProgressInfo.getCurrentSize()；总文件大小 v2ProgressInfo.getTotalSize()
                        }

                        override fun onError(code: Int, desc: String) {
                            // 下载失败
                        }

                        override fun onSuccess() {
                            // 下载完成
                        }
                    })
                } else {
                    // 文件已存在
                }

                // 设置视频文件路径，这里可以用 uuid 作为标识，避免重复下载
                val videoPath = "/sdcard/im/video/myUserID$videoUUID"
                val videoFile = File(videoPath)
                if (!videoFile.exists()) {
                    v2TIMVideoElem.downloadVideo(videoPath, object : V2TIMDownloadCallback {
                        override fun onProgress(progressInfo: V2TIMElem.V2ProgressInfo) {
                            // 下载进度回调：已下载大小 v2ProgressInfo.getCurrentSize()；总文件大小 v2ProgressInfo.getTotalSize()
                        }

                        override fun onError(code: Int, desc: String) {
                            // 下载失败
                        }

                        override fun onSuccess() {
                            // 下载完成
                        }
                    })
                } else {
                    // 文件已存在
                }
            }
            V2TIMMessage.V2TIM_ELEM_TYPE_SOUND -> {
                // 语音消息
                val v2TIMSoundElem = msg.soundElem
                // 语音 ID,内部标识，可用于外部缓存 key
                val uuid = v2TIMSoundElem.uuid
                // 语音文件大小
                val dataSize = v2TIMSoundElem.dataSize
                // 语音时长
                val duration = v2TIMSoundElem.duration
                // 设置语音文件路径 soundPath，这里可以用 uuid 作为标识，避免重复下载
                val soundPath = "/sdcard/im/sound/myUserID$uuid"
                val imageFile = File(soundPath)
                // 判断 soundPath 下有没有已经下载过的语音文件
                if (!imageFile.exists()) {
                    v2TIMSoundElem.downloadSound(soundPath, object : V2TIMDownloadCallback {
                        override fun onProgress(progressInfo: V2TIMElem.V2ProgressInfo) {
                            // 下载进度回调：已下载大小 v2ProgressInfo.getCurrentSize()；总文件大小 v2ProgressInfo.getTotalSize()
                        }

                        override fun onError(code: Int, desc: String) {
                            // 下载失败
                        }

                        override fun onSuccess() {
                            // 下载完成
                        }
                    })
                } else {
                    // 文件已存在
                }
            }
            V2TIMMessage.V2TIM_ELEM_TYPE_FILE -> {
                // 文件消息
                val v2TIMFileElem = msg.fileElem
                // 文件 ID,内部标识，可用于外部缓存 key
                val uuid = v2TIMFileElem.uuid
                // 文件名称
                val fileName = v2TIMFileElem.fileName
                // 文件大小
                val fileSize = v2TIMFileElem.fileSize
                // 设置文件路径，这里可以用 uuid 作为标识，避免重复下载
                val filePath = "/sdcard/im/file/myUserID$uuid"
                val file = File(filePath)
                if (!file.exists()) {
                    v2TIMFileElem.downloadFile(filePath, object : V2TIMDownloadCallback {
                        override fun onProgress(progressInfo: V2TIMElem.V2ProgressInfo) {
                            // 下载进度回调：已下载大小 v2ProgressInfo.getCurrentSize()；总文件大小 v2ProgressInfo.getTotalSize()
                        }

                        override fun onError(code: Int, desc: String) {
                            // 下载失败
                        }

                        override fun onSuccess() {
                            // 下载完成
                        }
                    })
                } else {
                    // 文件已存在
                }
            }
            V2TIMMessage.V2TIM_ELEM_TYPE_LOCATION -> {
                // 地理位置消息
                val v2TIMLocationElem = msg.locationElem
                // 地理位置信息描述
                val locationName = v2TIMLocationElem.desc
                // 经度
                // 经度
                val longitude = v2TIMLocationElem.longitude
                // 纬度
                // 纬度
                val latitude = v2TIMLocationElem.latitude
            }
            else -> {
                // 其他消息
            }
        }
    }

    /**
     * 消息已读通知
     * 接收端发送消息已读回执后,发送端可以在 V2TIMAdvancedMsgListener.onRecvMessageReadReceipts()中收到已读回执通知，
     * 在通知中更新UI,例如更新某条消息为"2人已读"。
     */
    override fun onRecvMessageReadReceipts(receiptList: MutableList<V2TIMMessageReceipt>?) {
        super.onRecvMessageReadReceipts(receiptList)
        receiptList?.forEach { receipt ->
            // 已读回执消息 ID
            val msgID: String = receipt.msgID
            //  C2C 消息对方 ID
            val userID = receipt.userID
            // C2C 消息对方已读状态
            val isPeerRead: Boolean = receipt.isPeerRead
            // 群消息最新已读数
            val readCount: Long = receipt.readCount
            // 群消息最新未读数
            val unreadCount: Long = receipt.unreadCount
        }
    }

    /**
     * 消息撤回通知
     * 只有被撤回的消息的接收方才会收到该回调,发送方调用revokeMessage方法
     */
    override fun onRecvMessageRevoked(msgID: String?) {
        super.onRecvMessageRevoked(msgID)
        // msgList 为当前聊天界面的消息列表
        val msgList = mutableListOf<V2TIMMessage>()
        msgList.forEach {
            if (it.msgID == msgID) {
                // msg 即为被撤回的消息，您需要修改 UI 上相应的消息气泡的状态
            } else {
                // msg 不是被撤回的消息
            }
        }
    }

    /**
     * 消息内容被修改通知
     * 只有被修改的消息的接收方才会收到该回调,发送方调用modifyMessage方法
     */
    override fun onRecvMessageModified(msg: V2TIMMessage?) {
        super.onRecvMessageModified(msg)
    }
}