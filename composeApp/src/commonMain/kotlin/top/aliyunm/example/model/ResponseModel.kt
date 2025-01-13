package top.aliyunm.example.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseModel<T>(
    val code: Int,
    val message: String? = null,
    val suggest: String? = null,
    val data: T? = null
)

@Serializable
data class ResponseNotDataModel(
    val code: Int,
    val message: String? = null
)

@Serializable
class ErrorModel