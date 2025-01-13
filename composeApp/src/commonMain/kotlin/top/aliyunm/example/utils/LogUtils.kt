package top.aliyunm.example.utils

import io.github.oshai.kotlinlogging.KotlinLogging

/**
 * 日志工具
 */
object LogUtils {

    private val logger = KotlinLogging.logger{}

    fun logi(log: Any) {
        println(log)
    }

    fun logd(log: Any) {
        logger.debug { log }
    }

    fun logw(log: Any) {
        logger.warn { log }
    }

    fun loge(log: Any) {
        logger.error { log }
    }
}