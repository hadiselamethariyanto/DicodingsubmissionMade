package com.bwx.made.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun formatStringDate(date: String): String {
        var format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val newDate = format.parse(date)!!
        format = SimpleDateFormat("dd MMMM yy", Locale.getDefault())
        return format.format(newDate)
    }
}