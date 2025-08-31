package cd.zgeniuscoders.zwallet.modules.income.domain.models

data class Income(
    val id: String = "",
    val userId: String = "",
    val amount: Double = 0.0,
    val description: String = "",
    val date: String = ""
)