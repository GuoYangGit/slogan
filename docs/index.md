# Android项目框架

> 本项目使用`Kotlin`语言编写，项目框架使用`MVVM+组件化`形式。

## 目录结构

- `App`  入口模块
- `module-xxx` 业务功能模块
- `sdk-xxx` 基础功能模块
- `mvvm` 中间架构模块

## Android版本适配

具体查看 [Android版本适配](./版本适配) 文档

> 其主要适配是权限类，本框架已经集成 [PermissionX](https://github.com/guolindev/PermissionX) 进行适配

## 三方依赖

### MVVM 模块

| 依赖                                                         | 描述                      |
| :----------------------------------------------------------- | ------------------------- |
| XlogUtils                                                    | Log日志库                 |
| AndroidBase                                                  | 基层Base库                |
| [rxhttp](https://github.com/liujingxing/rxhttp)              | 网络请求库                |
| [MMKV](https://dylancaicoding.github.io/MMKV-KTX/#/)         | 本地存储方案(MMKV)        |
| [Anchors](https://github.com/DSAppTeam/Anchors/blob/master/README-zh.md) | App启动框架               |
| [ImageExt](https://github.com/forJrking/ImageExt)            | 图片加载库(基于Glide封装) |
| [Longan](https://dylancaicoding.github.io/Longan/#/)         | Kotlin工具类              |
| [AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize/blob/master/README-zh.md) | 屏幕适配框架              |
| [PictureSelector](https://github.com/LuckSiege/PictureSelector/blob/version_component/README_CN.md) | 图片选择库                |
| [AgentWeb](https://github.com/Justson/AgentWeb)              | WebView加载库             |
| [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/0-BaseRecyclerViewAdapterHelper.md) | RecyclerViewAdapter       |
| [BackgroundLibrary](https://github.com/JavaNoober/BackgroundLibrary) | 标签生成shape             |
| [ViewBindingKTX](https://dylancaicoding.github.io/ViewBindingKTX/#/) | 视图绑定ViewBinding       |
| [StateLayout](https://liangjingkanji.github.io/StateLayout/) | 缺省页                    |
| [XPopup](https://github.com/li-xiaojun/XPopup)               | 弹窗                      |
| [UltimateBarX](https://github.com/Zackratos/UltimateBarX)    | 透明导航栏                |
| [BannerViewPager](https://github.com/zhpanvip/BannerViewPager) | 轮播图                    |
| [spannable](https://github.com/liangjingkanji/spannable)     | Spannable构建工具         |

