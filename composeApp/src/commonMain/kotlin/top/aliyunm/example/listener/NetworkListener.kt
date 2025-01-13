package top.aliyunm.example.listener

import top.aliyunm.example.platform.netWorkListener

/**
 * 网络监听器
 */
object NetworkListener {

    /**
     * 网络变化监听器集合
     */
    val listeners = mutableListOf<(Boolean) -> Unit>()

    /**
     * 是否有网络
     */
    private var isConnect: Boolean = false

    /**
     * 注册网络状态变化监听器
     */
    fun init() {
        netWorkListener {
            onNetworkChanged(it)
        }
    }

    /**
     * 网络状态变化回调
     * @param isConnected 是否连接上了网络
     */
    private fun onNetworkChanged(isConnected: Boolean) {
        isConnect = isConnected
        // 发通知
        listeners.forEach { it(isConnected) }
    }

    /**
     * 是否有网络
     */
    fun isConnect(): Boolean {
        return isConnect
    }
}