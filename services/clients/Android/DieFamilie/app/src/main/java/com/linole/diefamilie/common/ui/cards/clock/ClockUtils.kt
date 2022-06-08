package com.linole.diefamilie.common.ui.cards.clock

import java.util.*

data class Time(
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
    val hours: Int,
    val minutes: Int,
    val seconds: Int
) {
    fun clock(withSeconds: Boolean = false): String {
        val hourString = if (hours < 10) "0$hours" else "$hours"
        val minuteString = if (minutes < 10) "0$minutes" else "$minutes"
        val secondString = if (seconds < 10) "0$seconds" else "$seconds"
        "$hourString:$minuteString".let {
            return if (withSeconds) it.plus(":$secondString") else it
        }
    }

    fun date() = "$dayOfMonth/$month-$year"
}

fun currentTime(): Time = Calendar.getInstance().run {
    Time(
        get(Calendar.YEAR),
        get(Calendar.MONTH),
        get(Calendar.DAY_OF_MONTH),
        get(Calendar.HOUR_OF_DAY),
        get(Calendar.MINUTE),
        get(Calendar.SECOND)
    )
}