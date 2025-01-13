package top.aliyunm.example.platform

import platform.Foundation.NSBundle
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getLocalPath(): String {
    return NSBundle.mainBundle.bundlePath
}

actual fun getAbsolutePath(): String {
    return NSBundle.mainBundle.bundlePath
}

actual fun getExternalStoragePath(): String {
    return ""
}

actual fun getExternalCachePath(): String {
    return ""
}

actual fun netWorkListener(callback: (Boolean) -> Unit) {

}

/**
 * 获取缓存路径
 */
actual fun getCachePath(): String {
    return ""
}

actual fun setImmersiveMode() {

}

/**
 * 关闭软键盘
 */
actual fun closeKeyBoard() {

}