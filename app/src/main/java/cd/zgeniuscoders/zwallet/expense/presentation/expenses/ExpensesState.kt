package cd.zgeniuscoders.zwallet.expense.presentation.expenses

import cd.zgeniuscoders.zwallet.expense.presentation.models.ExpenseUi

data class ExpensesState(
    var isLoading: Boolean = false,
    var expenses: List<ExpenseUi> = emptyList(),
    var message: String = "",
    var showFilterDialog: Boolean = false,
    var showAddExpenseDialog: Boolean = false,
    var totalExpenses: Double = 0.0
)
