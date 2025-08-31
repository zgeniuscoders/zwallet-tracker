package cd.zgeniuscoders.zwallet.modules.debts.domain.models

data class Debt(
    val id: String = "",
    val userId: String = "",
    val amount: Double,
    val description: String,
    val creditorName: String,
    val dueDate: String,
    val isOwedByMe: Boolean = false,
    val isPaid: Boolean = false
)