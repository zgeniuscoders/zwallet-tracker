package cd.zgeniuscoders.zwallet.domain.models

import java.time.LocalDateTime

data class Income(
    val id: String = "",
    val amount: Double,
    val description: String,
    val date: LocalDateTime = LocalDateTime.now()
)
