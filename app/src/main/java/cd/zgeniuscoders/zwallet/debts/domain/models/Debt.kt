package cd.zgeniuscoders.zwallet.debts.domain.models

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