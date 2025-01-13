package top.aliyunm.example.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * 日期工具
 */
object DateUtils {
    /**
     * 获取当前本地时间
     */
    val now: LocalDateTime
        get() {
            val now = Clock.System.now()
            val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
            return localDateTime
        }

    /**
     * 获取当前本地时间戳
     */
    val timestamp: Long
        get() {
            val now = Clock.System.now()
            return now.toEpochMilliseconds()
        }

    /**
     * 将时间戳转换为LocalDateTime
     */
    fun timestampToLocalDateTime(timestamp: Long): LocalDateTime {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        return instant.toLocalDateTime(TimeZone.currentSystemDefault())
    }

    /**
     * 日期时间转到日期(抹除时间)
     */
    fun localDateTime2LocalDate() {

    }

    /**
     * 日期转到日期时间(添加时间 00:00:00)
     */
    fun localDate2LocalDateTime() {

    }

    /**
     * LocalDateTime转时间戳
     */
    fun localDateTimeToTimestamp(localDateTime: LocalDateTime): Long {
        return localDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }

    /**
     * 日期字符串转LocalDateTime
     */
    fun dateStr2LocalDateTime(dateStr: String): LocalDateTime {
        return LocalDateTime.parse(dateStr)
    }

    /**
     * 根据LocalDateTime获取在一年中的第几天
     */
    fun getDayOfYear(localDate: LocalDate): Int {
        return localDate.dayOfYear
    }

    /**
     * 是否闰年
     */
    fun isLeap(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }

    /**
     * 一个月有多少天
     */
    fun getDaysOfMonth(year: Int, month: Int): Int {
        return when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            2 -> if (isLeap(year)) 29 else 28
            else -> 30
        }
    }

    /**
     * 根据年月日生成[LocalDate]
     */
    fun date2LocalDate(year: Int, month: Int, day: Int): LocalDate {
        var temDay = day
        if (temDay > getDaysOfMonth(year, month)) {
            temDay = getDaysOfMonth(year, month)
        }
        return LocalDate(year, month, temDay)
    }

    /**
     * 根据年月日时分生成[LocalDateTime]
     */
    fun date2LocalDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int): LocalDateTime {
        return LocalDateTime(year, month, day, hour, minute)
    }

    /**
     * 加减日期
     * @param localDateTime 原始日期
     * @param day 需要加减的天数
     */
    fun modifyDate(
        localDateTime: LocalDateTime,
        day: Int
    ): LocalDateTime {
        val instant = localDateTime.toInstant(TimeZone.currentSystemDefault())
        if (day != 0) {
            val duration = day.toDuration(DurationUnit.DAYS)
            instant.plus(duration)
        }
        return instant.toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun formatDate(localDate: LocalDate): String {
        return LocalDate.Formats.ISO.format(localDate)
    }

    fun formatDateTime(localDateTime: LocalDateTime): String {
        return LocalDateTime.Formats.ISO.format(localDateTime)
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    fun formatDate(localDate: LocalDate, format: String = "yyyy-MM-dd"): String {
        return LocalDate.Format { byUnicodePattern(format) }.format(localDate)
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    fun formatDateTime(
        localDateTime: LocalDateTime,
        format: String = "yyyy-MM-dd HH:mm:ss"
    ): String {
        return LocalDateTime.Format { byUnicodePattern(format) }.format(localDateTime)
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    fun toLocalDate(dateStr: String, format: String = "yyyy-MM-dd"): LocalDate {
        return LocalDate.Format { byUnicodePattern(format) }.parse(dateStr)
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    fun toLocalDateTime(dateTimeStr: String, format: String = "yyyy-MM-dd HH:mm:ss"): LocalDateTime {
        return LocalDateTime.Format { byUnicodePattern(format) }.parse(dateTimeStr)
    }
}