package top.aliyunm.example.utils

/**
 * 对象处理工具
 */
object ObjectUtils {

    inline fun <reified T : Any> toMap(any: T): Map<String, Any?> {
        val map: Map<String, Any> = hashMapOf()
        return map
    }
}