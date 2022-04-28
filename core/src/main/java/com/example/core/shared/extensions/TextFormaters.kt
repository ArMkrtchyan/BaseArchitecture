package com.example.core.shared.extensions

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToLong

object TextFormaters {
    @JvmStatic
    fun Double.format(): String {
        val formatter = NumberFormat.getInstance()
        formatter.maximumFractionDigits = 2
        return "${formatter.format(this.roundToLong())}"
    }

    @JvmStatic
    fun Float.format(): String {
        val formatter = NumberFormat.getInstance()
        formatter.maximumFractionDigits = 2
        return "${formatter.format(this.roundToLong())}"
    }

    @JvmStatic
    fun Int.format(): String {
        val formatter = NumberFormat.getInstance()
        formatter.maximumFractionDigits = 2
        return "${formatter.format(this.toLong())}"
    }

    @JvmStatic
    fun Long.format(): String {
        val formatter = NumberFormat.getInstance()
        formatter.maximumFractionDigits = 2
        return "${formatter.format(this)}"
    }

    @JvmStatic
    fun Long.formatNumber(): String {
        val formatter = NumberFormat.getInstance(Locale("en")) as DecimalFormat
        formatter.minimumIntegerDigits = 6
        val symbols = formatter.decimalFormatSymbols
        formatter.decimalFormatSymbols = symbols
        return "#${formatter.format(this)}".replace(",", "").replace(" ", "")
    }

    @JvmStatic
    fun Int.formatNumber(): String {
        val formatter = NumberFormat.getInstance(Locale("en")) as DecimalFormat
        formatter.minimumIntegerDigits = 6
        val symbols = formatter.decimalFormatSymbols
        formatter.decimalFormatSymbols = symbols
        return "#${formatter.format(this)}".replace(",", "").replace(" ", "")
    }
}