package cd.zgeniuscoders.zwallet.expense.domain.models

import cd.zgeniuscoders.zwallet.expense.domain.enums.ExpenseCategory
import java.time.LocalDateTime

data class Expense(
    val id: String = "",
    var userId: String = "",
    val amount: Double,
    val description: String,
    val observation: String = "",
    val category: ExpenseCategory,
    val date: LocalDateTime = LocalDateTime.now()
)

