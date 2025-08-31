package cd.zgeniuscoders.zwallet.modules.debts.presentation.models

import java.time.LocalDateTime

data class DebtUi(
    val id: String,
    val isOverdue: Boolean,
    val week: Boolean,
    val isOwedByMe: Boolean,
    val date: String,
    val creditor: String,
    val description: String,
    val amount: Double,
    val amountFormat: String,
)
