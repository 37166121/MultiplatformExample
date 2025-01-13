package top.aliyunm.example.platform

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Environment
import top.aliyunm.example.IApplication

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getLocalPath(): String {
    return IApplication.context.filesDir.path
}

actual fun getAbsolutePath(): String {
    return IApplication.context.filesDir.absolutePath
}

actual fun getExternalStoragePath(): String {
    return Environment.getExternalStorageDirectory().absolutePath
}

actual fun getExternalCachePath(): String {
    return IApplication.context.externalCacheDir?.absolutePath ?: ""
}

actual fun netWorkListener(callback: (Boolean) -> Unit) {
    val request = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()
    val manager = IApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    manager.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            callback(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
            super.onCapabilitiesChanged(network, networkCapabilities)
        }
    })
}

/**
 * 获取缓存路径
 */
actual fun getCachePath(): String {
    return IApplication.context.cacheDir.path
}

actual fun setImmersiveMode() {

}

/**
 * 关闭软键盘
 */
actual fun closeKeyBoard() {

}