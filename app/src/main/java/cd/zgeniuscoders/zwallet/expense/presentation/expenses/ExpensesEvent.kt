package cd.zgeniuscoders.zwallet.expense.presentation.expenses

import cd.zgeniuscoders.zwallet.expense.domain.models.Expense

sealed interface ExpensesEvent {
    data object OnAddExpense : ExpensesEvent
    data object OnFilterExpense : ExpensesEvent
    data class AddExpense(var data: Expense) : ExpensesEvent
}