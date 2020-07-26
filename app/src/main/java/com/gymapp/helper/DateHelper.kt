package com.gymapp.helper

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatterBuilder
import org.threeten.bp.temporal.ChronoField

object DateHelper {

    val dateTimeFormatter = DateTimeFormatterBuilder()
        .appendPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
        .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
        .toFormatter()

    val hourTimeFormatter = DateTimeFormatterBuilder()
        .appendPattern("hh:mm")
        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
        .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
        .toFormatter()

    val amPmTimeFormatter = DateTimeFormatterBuilder()
        .appendPattern("a")
        .toFormatter()


    fun getGymDetailTime(startTime: String?, endTime: String?): String {

        if (startTime.isNullOrEmpty() || endTime.isNullOrEmpty() || startTime.contains("null")) {
            return "-"
        }

        val begin = (LocalDateTime.parse(startTime, dateTimeFormatter).atOffset(
            ZoneOffset.UTC
        ).toInstant()).atZone(ZoneId.of("Asia/Dubai"))

        val end = (LocalDateTime.parse(endTime, dateTimeFormatter).atOffset(
            ZoneOffset.UTC
        ).toInstant()).atZone(ZoneId.of("Asia/Dubai"))


        val startHour = begin.format(hourTimeFormatter).removePrefix("0").replace(" ", "")
            .replace(":00", "")

        val endHour =
            end.format(hourTimeFormatter).removePrefix("0").replace(" ", "").replace(":00", "")

        val startAmPmFormat = amPmTimeFormatter.format(begin)
        val endAmPmFormat = amPmTimeFormatter.format(end)

        return "$startHour $startAmPmFormat - $endHour $endAmPmFormat"
    }
}