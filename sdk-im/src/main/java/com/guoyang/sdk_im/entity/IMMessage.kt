package com.guoyang.sdk_im.entity

/**
 * @author yang.guo on 2022/11/8
 * @describe 发送消息类型的基础类
 */
open class IMMessage(messageType: MessageType) {
    var msgId = "" // 消息id
    var timestamp: Long = 0 // 消息时间戳
    var sender: String = "" // 发送者
    var nickName: String = "" // 发送者昵称
    var avatar: String = "" // 发送者头像
    var receiver: String = "" // 接收者
    var isGroup: Boolean = false // 是否是群聊
    var state: IMMessageState = IMMessageState.SENDING // 消息状态
}

/**
 * 文本消息类型
 */
class IMTextMessage(val content: String) : IMMessage(MessageType.TXT)

/**
 * 图片消息类型
 */
class IMImageMessage(val path: String) : IMMessage(MessageType.IMAGE)

/**
 * 语音消息类型
 */
class IMAudioMessage(val path: String, val duration: Int) : IMMessage(MessageType.AUDIO)

/**
 * 视频消息类型
 */
class IMVideoMessage(
    val path: String, val duration: Int, val coverPath: String
) : IMMessage(MessageType.VIDEO)

/**
 * 文件消息类型
 */
class IMFileMessage(val path: String) : IMMessage(MessageType.FILE)

/**
 * 定位消息类型
 */
class LocationMessage(val locationName: String, val latitude: Double, val longitude: Double) :
    IMMessage(MessageType.LOCATION)

/**
 * 自定义消息类型
 */
class IMCustomMessage(val content: String) : IMMessage(MessageType.CUSTOM)

/**
 * 提示消息类型
 */
class IMTipMessage(val content: String) : IMMessage(MessageType.TIP)

/**
 * 系统消息类型
 */
class IMSysMessage(val content: String) : IMMessage(MessageType.SYSTEM)

/**
 * 创建文本消息
 * @param content 文本消息
 * @param apply 配置消息
 * @return 文本消息
 */
inline fun IMMessage.createTxtMessage(
    content: String, apply: IMTextMessage.() -> Unit = {}
): IMMessage = IMTextMessage(content).apply(apply)

/**
 * 创建图片消息
 * @param path 图片路径
 * @param apply 配置消息
 * @return 图片消息
 */
inline fun IMMessage.createImageMessage(
    path: String, apply: IMImageMessage.() -> Unit = {}
): IMMessage = IMImageMessage(path).apply(apply)

/**
 * 创建语音消息
 * @param path 语音路径
 * @param duration 语音时长
 * @param apply 配置消息
 * @return 语音消息
 */
inline fun IMMessage.createAudioMessage(
    path: String, duration: Int, apply: IMAudioMessage.() -> Unit = {}
): IMMessage = IMAudioMessage(path, duration).apply(apply)

/**
 * 创建视频消息
 * @param path 视频路径
 * @param duration 视频时长
 * @param coverPath 视频封面路径
 * @param apply 配置消息
 * @return 视频消息
 */
inline fun IMMessage.createVideoMessage(
    path: String, duration: Int, coverPath: String, apply: IMVideoMessage.() -> Unit = {}
): IMMessage = IMVideoMessage(path, duration, coverPath).apply(apply)

/**
 * 创建文件消息
 * @param path 文件路径
 * @param apply 配置消息
 * @return 文件消息
 */
inline fun IMMessage.createFileMessage(
    path: String, apply: IMFileMessage.() -> Unit = {}
): IMMessage = IMFileMessage(path).apply(apply)

/**
 * 创建定位消息
 * @param locationName 位置名称
 * @param latitude 纬度
 * @param longitude 经度
 * @param apply 配置消息
 * @return 定位消息
 */
inline fun IMMessage.createLocationMessage(
    locationName: String,
    latitude: Double,
    longitude: Double,
    apply: LocationMessage.() -> Unit = {}
): IMMessage = LocationMessage(locationName, latitude, longitude).apply(apply)

/**
 * 创建自定义消息
 * @param content 自定义消息数据
 * @param apply 配置消息
 * @return 自定义消息
 */
inline fun IMMessage.createCustomMessage(
    content: String, apply: IMCustomMessage.() -> Unit = {}
): IMMessage = IMCustomMessage(content).apply(apply)

/**
 * 创建提示消息
 * @param content 提示消息内容
 * @param apply 配置消息
 * @return 提示消息
 */
inline fun IMMessage.createTipMessage(
    content: String, apply: IMTipMessage.() -> Unit = {}
): IMMessage = IMTipMessage(content).apply(apply)

/**
 * 创建系统消息
 * @param content 系统消息内容
 * @param apply 配置消息
 * @return 系统消息
 */
inline fun IMMessage.createSysMessage(
    content: String, apply: IMSysMessage.() -> Unit = {}
): IMMessage = IMSysMessage(content).apply(apply)