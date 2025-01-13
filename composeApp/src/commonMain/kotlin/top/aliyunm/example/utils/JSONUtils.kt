package top.aliyunm.example.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

/**
 * 序列化工具
 */
object JSONUtils {
    /**
     * [Serializable]对象转json
     */
    inline fun <reified T> toJson(any: T): String {
        return Json.encodeToString(any)
    }

    /**
     * json转[Serializable]对象
     */
    inline fun <reified T> fromJson(any: String): T {
        return Json { ignoreUnknownKeys = true }.decodeFromString<T>(any)
    }

    // TODO 自定义序列化器

}