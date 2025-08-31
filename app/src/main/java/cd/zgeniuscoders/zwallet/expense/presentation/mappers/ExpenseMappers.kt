package cd.zgeniuscoders.zwallet.expense.presentation.mappers

import androidx.compose.ui.text.capitalize
import cd.zgeniuscoders.zwallet.core.utils.formatDate
import cd.zgeniuscoders.zwallet.expense.domain.models.Expense
import cd.zgeniuscoders.zwallet.expense.presentation.models.ExpenseUi

fun List<Expense>.toExpenseUiModel(): List<ExpenseUi> {

    return this.map {
        ExpenseUi(
            observation = it.observation,
            date = it.date.formatDate()?:"",
            amountFormat = "${it.amount}$",
            description = it.description,
            amount = it.amount,
            category = it.category.displayName,
            color = it.category.color
        )
    }
}