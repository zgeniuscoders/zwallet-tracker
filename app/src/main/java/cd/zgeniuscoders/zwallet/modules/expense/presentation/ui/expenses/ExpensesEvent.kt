package cd.zgeniuscoders.zwallet.modules.expense.presentation.ui.expenses

import cd.zgeniuscoders.zwallet.modules.expense.domain.models.Expense


sealed interface ExpensesEvent {
    data object OnAddExpense : ExpensesEvent
    data object OnFilterExpense : ExpensesEvent
    data class AddExpense(var data: Expense) : ExpensesEvent
}