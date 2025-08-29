package top.aliyunm.example.http

import top.aliyunm.example.Ext.fromJson
import top.aliyunm.example.model.ResponseNotDataModel
import top.aliyunm.example.state.NetworkErrorState
import top.aliyunm.example.utils.LogUtils
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.plugin
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsBytes
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.content.TextContent
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.toMap
import kotlinx.serialization.json.Json

object ApiConfig {

    const val ms = "http://127.0.0.1/"

    /**
     * 响应错误码白名单(放到页面中处理)
     */
    val codeWhiteList = listOf(200)

    /**
     * 请求客户端
     */
    private val client: HttpClient = HttpClient {
        // 请求配置
        install(DefaultRequest) {
            contentType(ContentType.Application.Json)
            url {
                protocol = URLProtocol.HTTPS
                host = getDomain()
            }
        }
        // 内容序列化
        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
        // 超时
        install(HttpTimeout) {
            requestTimeoutMillis = 60_000
            connectTimeoutMillis = 15_000
        }
        // Cookie管理
        install(HttpCookies) {
            storage = AcceptAllCookiesStorage()
        }
    }.apply {
        // 请求拦截
        plugin(HttpSend).intercept { request ->
            // 弹出加载框
            NetworkErrorState.showRequestDialog(true)
            val body = when {
                request.body is TextContent -> (request.body as TextContent).text
                else -> ""
            }
            LogUtils.logi(
                """
                    ========请求========
                    请求URL：${request.url}
                    请求Header：${request.headers.build().toMap()}
                    请求参数：$body
                    ========请求========
                """.trimIndent()
            )

            // 请求
            val call = execute(request)

            // 关闭加载框
            NetworkErrorState.closeRequestDialog()

            val isText = try {
                call.response.bodyAsText()
                true
            } catch (e: Exception) {
                false
            }

            LogUtils.logi(
                """
                    ========响应========
                    请求URL：${request.url}
                    响应Header：${call.response.headers.toMap()}
                    响应参数：${if (isText) call.response.bodyAsText() else call.response.bodyAsBytes()}
                    ========响应========
                """.trimIndent()
            )

            if (isText) {
                try {
                    val body = call.response.bodyAsText()
                    val response = body.fromJson<ResponseNotDataModel>()
                    if (response.code != 200 || response.message != "success") {
                        if (!codeWhiteList.contains(response.code)) {
                            NetworkErrorState.showError(response.message ?: "")
                        }
                        // 重试
                        execute(request)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return@intercept call
                }
            }
            call
        }
    }

    /**
     * 获取域名
     */
    fun getBaseUrl(): String {
        return ms
    }

    /**
     * 获取域名
     */
    fun getDomain(): String {
        return getBaseUrl().split("://")[1].split("/")[0]
    }

    /**
     * POST请求
     * @return 返回[T]
     */
    suspend inline fun <reified T> postAny(
        url: String,
        params: Any? = null,
        contentType: ContentType = ContentType.Application.Json,
        callback: (T) -> Unit
    ) {
        val response = post(url, params, contentType)
        callback(response.body<T>())
    }

    /**
     * POST请求
     * @return 返回[HttpResponse]
     */
    suspend fun post(
        url: String,
        params: Any? = null,
        contentType: ContentType = ContentType.Application.Json
    ): HttpResponse {
        val response = client.post(url) {
            contentType(contentType)
            setBody(params)
        }
        return response
    }

    /**
     * GET请求
     * @return 返回[T]
     */
    suspend inline fun <reified T> getAny(url: String): T {
        return get(url).body<T>()
    }

    /**
     * GET请求
     * @return 返回[HttpResponse]
     */
    suspend fun get(url: String): HttpResponse {
        val response = client.get(url) {

        }
        return response
    }

    /**
     * Form-Data请求
     * @param encodeInQuery `true`: GET请求(默认), `false`: POST请求
     * @return 返回[T]
     */
    suspend inline fun <reified T> formAny(
        url: String,
        params: HashMap<String, String> = hashMapOf(),
        encodeInQuery: Boolean = true
    ): T {
        return form(url, params, encodeInQuery).body<T>()
    }

    /**
     * Form-Data请求
     * @return 返回[HttpResponse]
     */
    suspend fun form(
        url: String,
        params: HashMap<String, String> = hashMapOf(),
        encodeInQuery: Boolean = true
    ): HttpResponse {
        val response = client.submitForm(
            url = url,
            encodeInQuery = encodeInQuery,
            formParameters = parameters {
                params.keys.forEach {
                    append(it, params[it] ?: "")
                }
            }
        )
        return response
    }
}