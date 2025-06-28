package cd.zgeniuscoders.zwallet.expense.domain.models

import java.time.LocalDateTime

data class Debt(
    val id: String = "",
    val userId: String = "",
    val amount: Double = 0.0,
    val description: String = "",
    val creditorName: String = "",
    val dueDate: String = "",
    val isOwedByMe: Boolean = false, // true si je dois, false si on me doit
    val isPaid: Boolean = false
)
