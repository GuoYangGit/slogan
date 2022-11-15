# Android项目框架

> 本项目使用`Kotlin`语言编写，项目框架使用`MVVM+组件化`形式。

## 知识储备

> 在项目编写代码前，需要对项目使用的技术点进行提前的了解和掌握，下面罗列一些必须掌握的技术点。

| 技术点   | 推荐文章                                         |
| -------- | ------------------------------------------------ |
| `kotlin` | [官方文档](https://developer.android.com/kotlin) |
| `TheRouter` | [没错，TheRouter 是我写的](https://juejin.cn/post/7140153936295493668) |
| `Hilt` | [官方文档](https://developer.android.com/training/dependency-injection/hilt-android) |
| `Lifecycle` | [“终于懂了“系列：Jetpack AAC完整解析（一）Lifecycle 完全掌握！](https://juejin.cn/post/6893870636733890574) |
| `LiveData` | [“终于懂了“系列：Jetpack AAC完整解析（二）LiveData 完全掌握！](https://juejin.cn/post/6903143273737814029/) |
| `ViewModel` | [““终于懂了“系列：Jetpack AAC完整解析（三）ViewModel 完全掌握！](https://juejin.cn/post/6915012483421831175) |
| `MVVM` | [“终于懂了“系列：Jetpack AAC完整解析（四）MVVM - Android架构探索！](https://juejin.cn/post/6921321173661777933) |
| `ROOM` | [官方文档](https://developer.android.com/training/data-storage/room) |
| `Flow` | [Jetpack 系列（4）—— 有小伙伴说看不懂 LiveData、Flow、Channel，跟我走](https://juejin.cn/post/7077149853876224013) |

待补充...

## 项目结构

- `app`  入口模块
- `module-xxx` [业务功能模块](./module/业务模块指南.md)
- `mvvm` [中间架构模块](./module/架构模块指南.md)
- `sdk-xxx` [基础功能模块](./module/基础模块指南.md)



## 编码指南

该文章讲述了业务功能如何快速实现，包括常用的功能介绍。[点此查看](./代码编写指南.md)

## 其他指南

- [6.0动态权限申请](./other/动态权限申请.md)
- [项目Gradle配置](./other/Gradle.md)
- [Android 版本适配](./other/版本适配.md)
- [代码规范](./other/代码规范.md)
