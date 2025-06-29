package cd.zgeniuscoders.zwallet.expense.presentation.income

import cd.zgeniuscoders.zwallet.expense.domain.enums.FilterPeriod
import cd.zgeniuscoders.zwallet.expense.domain.models.Income

data class IncomeState(
    var isLoading: Boolean = false,
    var message: String = "",
    var showAddIncomeDialog: Boolean = false,
    var showFilterDialog: Boolean = false,
    var selectedFilter: FilterPeriod = FilterPeriod.MONTH,
    var incomes: List<Income> = emptyList()
)
