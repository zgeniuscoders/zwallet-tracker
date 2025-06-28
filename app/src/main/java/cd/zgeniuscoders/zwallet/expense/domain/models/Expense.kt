package cd.zgeniuscoders.zwallet.expense.domain.models

import cd.zgeniuscoders.zwallet.expense.domain.enums.ExpenseCategory
import java.time.LocalDateTime

data class Expense(
    val id: String = "",
    var userId: String = "",
    val amount: Double = 0.0,
    val description: String = "",
    val observation: String = "",
    val category: ExpenseCategory = ExpenseCategory.NORMAL,
    val date: String = ""
)

