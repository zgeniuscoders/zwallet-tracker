package cd.zgeniuscoders.zwallet.domain.models

import cd.zgeniuscoders.zwallet.domain.enums.ExpenseCategory
import java.time.LocalDateTime

data class Expense(
    val id: String = "",
    val amount: Double,
    val description: String,
    val observation: String = "",
    val category: ExpenseCategory,
    val date: LocalDateTime = LocalDateTime.now()
)

