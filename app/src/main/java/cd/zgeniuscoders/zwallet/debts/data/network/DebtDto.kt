package cd.zgeniuscoders.zwallet.debts.data.network

data class DebtDto(
    val id: String = "",
    val userId: String = "",
    val amount: Double = 0.0,
    val description: String = "",
    val creditorName: String = "",
    val dueDate: String = "",
    val owedByMe: Boolean = false,
    val paid: Boolean = false
)
