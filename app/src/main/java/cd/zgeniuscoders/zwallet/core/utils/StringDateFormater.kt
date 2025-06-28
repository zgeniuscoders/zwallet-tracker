package cd.zgeniuscoders.zwallet.core.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.formatDate(): String? {
    var inputFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    var outputFormater = DateTimeFormatter.ofPattern("d/M/yyyy")
    var dateTime = LocalDateTime.parse(this, inputFormater)
    return dateTime.format(outputFormater)
}

fun String.toLocalDate(): LocalDateTime {
    var inputFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
    var localDateTime = LocalDateTime.parse(this, inputFormater)
    return localDateTime
}