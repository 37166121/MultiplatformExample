package top.aliyunm.example

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import top.aliyunm.example.utils.DateUtils
import top.aliyunm.example.utils.FileUtils
import top.aliyunm.example.utils.JSONUtils
import io.ktor.utils.io.core.toByteArray
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import okio.Path
import top.aliyunm.example.utils.LogUtils

/**
 * 扩展函数
 */
object Ext {

    /// 导航扩展函数
    /**
     * 返回上一页
     */
    fun NavHostController?.back() {
        this?.popBackStack()
    }

    /**
     * 返回到某一页
     */
    fun NavHostController?.backTo(page: String) {
        this?.popBackStack(page, false)
    }

    /**
     * 前往某一页
     */
    fun NavHostController?.next(page: String, singleTop: Boolean = true) {
        this?.navigate(page, navOptions = NavOptions.Builder().setLaunchSingleTop(singleTop).build())
    }

    /**
     * 返回到某一页并清除中间页面
     */
    fun NavHostController?.nextAndClearPath(fromPage: String, toPage: String) {
        this?.navigate(toPage) { popUpTo(fromPage) { inclusive = false } }
        stack()
    }

    /**
     * 返回到某一页并清除中间和目标页面
     */
    fun NavHostController?.nextAndClearThis(fromPage: String, toPage: String) {
        this?.navigate(toPage) { popUpTo(fromPage) { inclusive = true } }
        stack()
    }

    /**
     * 获取当前页面的路由
     */
    fun NavHostController?.getRouter(): String {
        return this?.currentBackStackEntry?.destination?.route ?: ""
    }

    fun NavHostController?.findRouter(router: String): NavDestination? {
        return this?.findDestination(router)
    }

    /**
     * 查看导航堆栈
     */
    fun NavHostController?.stack() {
        this?.currentBackStack?.value?.forEach {
            LogUtils.logi(it.destination.route ?: "")
        }
    }

    /**
     * 判断是否为空，为空则抛出异常
     * @param exception 异常
     * @return 原对象
     */
    fun <T : Any?> T.isNull(exception: RuntimeException = NullPointerException()): T {
        if (this == null) {
            throw exception
        }
        return this
    }

    fun String.str2byte(): ByteArray {
        return this.toByteArray()
    }

    fun ByteArray.byte2Str(): String {
        return this.decodeToString()
    }

    fun String.toPath(): Path {
        return FileUtils.toPath(this)
    }

    inline fun<reified T> T.toJson(): String {
        return JSONUtils.toJson(this)
    }

    inline fun <reified T> String.fromJson(): T {
        return JSONUtils.fromJson<T>(this)
    }

    fun<T> T.toMap(): Map<String, Any?> {
        return hashMapOf()
    }

    /// 日期时间转换
    fun LocalDateTime.modifyDate(day: Int): LocalDateTime {
        return DateUtils.modifyDate(this, day)
    }

    fun LocalDateTime.formatDateTime(format: String = "yyyy-MM-dd HH:mm:ss"): String {
        return DateUtils.formatDateTime(this, format)
    }

    fun LocalDateTime.formatDate(format: String = "yyyy-MM-dd"): String {
        return DateUtils.formatDateTime(this, format)
    }

    fun LocalDate.formatDate(format: String = "yyyy-MM-dd"): String {
        return DateUtils.formatDate(this, format)
    }

    fun String.toLocalDateTime(format: String = "yyyy-MM-dd HH:mm:ss"): LocalDateTime {
        return DateUtils.toLocalDateTime(this, format)
    }

    fun String.toLocalDate(format: String = "yyyy-MM-dd"): LocalDate {
        return DateUtils.toLocalDate(this, format)
    }

    fun Modifier.dashedDivider(strokeWidth: Dp, color: Color) = drawBehind {
        drawIntoCanvas {
            val paint = Paint().apply {
                this.strokeWidth = strokeWidth.toPx()
                this.color = color
                style = PaintingStyle.Stroke
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            }
            it.drawLine(
                Offset(0f, size.height / 2),
                Offset(size.width, size.height / 2),
                paint
            )
        }
    }
}