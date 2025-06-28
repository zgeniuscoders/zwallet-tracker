package cd.zgeniuscoders.zwallet.domain.models

import java.time.LocalDateTime

data class Debt(
    val id: String = "",
    val amount: Double,
    val description: String,
    val creditorName: String,
    val dueDate: LocalDateTime,
    val isOwedByMe: Boolean, // true si je dois, false si on me doit
    val isPaid: Boolean = false
)
