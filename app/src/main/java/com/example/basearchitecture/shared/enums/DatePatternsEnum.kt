package com.armboldmind.am.delivery.shared.enums

import java.text.SimpleDateFormat
import java.util.*

enum class DatePatternsEnum(val pattern: String) {
    UTC("yyyy-MM-dd'T'HH:mm:ss"),
    DAY_MONTH_YEAR("dd MMMM yyyy"),
    DAY_MONTH_YEAR_NUMBER("dd/MM/yyyy"),
    DAY_MON_YEAR("dd-MMM-yyyy"),
    YEAR_MON_DAY("yyyy-MM-dd"),
    DAY_MONTH_YEAR_TIME_PATTERN("dd MMM yyyy HH:mm"),
    DAY_MONTH_YEAR_TIME_PATTERN2("dd.MM.yyyy HH:mm"),
    DAY_MONTH_TIME_PATTERN("d MMM HH:mm"),
    HOUR_MINUTE_TIME_PATTERN("HH:mm"),
    MOUNT_YEAR_PATTERN("MMM, yyyy"),
    WEEK_DAY_MOUNT_PATTERN("EEEE d MMM"),
    MOUNT_DAY_YEAR_PATTERN("MMMM d yyyy"),
    DAY_MONTH_SHORT("dd/MM"),
    YEAR("yyyy"),
    SERVER_ISO_PATTERN("yyyy-MM-dd'T'HH:mm:ss");

    fun formatDateToPattern(date: Date?): String? {
        return try {
            SimpleDateFormat(pattern, Locale.getDefault()).apply { }.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun formatIsoToDate(date: String): Date? {
        return try {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).apply { timeZone = TimeZone.getTimeZone("UTC") }.parse(date)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun formatIsoToPattern(date: String): String? {
        return try {
            formatDateToPattern(formatIsoToDate(date))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
