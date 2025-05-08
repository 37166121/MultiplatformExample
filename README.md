This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

# 目录树

## [androidMain Android平台](composeApp/src/androidMain)

## [commonMain 通用代码](composeApp/src/commonMain)
```
|- composeResources     公共资源
|- kotlin
  |- base               基础类
  |- common             公共类
  |- http               网络
  |- listener           监听器
  |- messageQueue       消息队列
  |- model              数据类型
  |- platform           分发到各自平台处理
  |- router             路由
  |- state              状态管理
  |- ui                 页面
  |- utils              工具
  |- viewmodel          状态管理
  |- App.kt             APP的初始化
  |- AppConfig.kt       App配置
  |- Ext.kt             扩展函数
  |- Keys.kt            Key目录
```

## [iosMain iOS平台](composeApp/src/iosMain)

