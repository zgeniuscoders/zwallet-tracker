package cd.zgeniuscoders.zwallet.core.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.formatDate(): String? {
    val outputFormater = DateTimeFormatter.ofPattern("d/mm/yyyy")
    val dateTime = LocalDateTime.parse(this)
    return dateTime.format(outputFormater)
}

fun String.toLocalDate(): LocalDateTime {
    val localDateTime = LocalDateTime.parse(this)
    return localDateTime
}