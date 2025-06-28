package cd.zgeniuscoders.zwallet.expense.presentation.expenses

import cd.zgeniuscoders.zwallet.expense.domain.models.Expense

data class ExpensesState(
    var isLoading: Boolean = false,
    var expenses: List<Expense> = emptyList(),
    var message: String = "",
    var showFilterDialog: Boolean = false,
    var showAddExpenseDialog: Boolean = false,
    var totalExpenses: Double = 0.0
)
