package top.aliyunm.example.platform

/**
 * 每个平台各自需要实现的接口
 */
interface Platform {
    val name: String
}

expect fun setImmersiveMode()

/**
 * 获取平台信息
 */
expect fun getPlatform(): Platform

/**
 * 获取APP本地路径
 */
expect fun getLocalPath(): String

/**
 * 获取APP绝对路径
 */
expect fun getAbsolutePath(): String

/**
 * 获取缓存路径
 */
expect fun getCachePath(): String

/**
 * 获取外部路径
 */
expect fun getExternalStoragePath(): String

/**
 * 获取外部缓存路径
 */
expect fun getExternalCachePath(): String

/**
 * 网络监听
 */
expect fun netWorkListener(callback: (Boolean) -> Unit)

/**
 * 关闭软键盘
 */
expect fun closeKeyBoard()