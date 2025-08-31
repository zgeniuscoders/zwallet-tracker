package cd.zgeniuscoders.zwallet.modules.income.presentation.ui.income

import cd.zgeniuscoders.zwallet.modules.expense.domain.enums.FilterPeriod
import cd.zgeniuscoders.zwallet.modules.income.presentation.models.IncomeUi

data class IncomeState(
    var isLoading: Boolean = false,
    var message: String = "",
    var showAddIncomeDialog: Boolean = false,
    var showFilterDialog: Boolean = false,
    var selectedFilter: FilterPeriod = FilterPeriod.MONTH,
    var incomes: List<IncomeUi> = emptyList()
)
