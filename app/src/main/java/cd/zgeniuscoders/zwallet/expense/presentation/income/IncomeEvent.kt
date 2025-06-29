package cd.zgeniuscoders.zwallet.expense.presentation.income

import cd.zgeniuscoders.zwallet.expense.domain.enums.FilterPeriod
import cd.zgeniuscoders.zwallet.expense.domain.models.Income

sealed interface IncomeEvent {
    data object OnShowAddIncomeDialog : IncomeEvent
    data object OnShowFilterDialog : IncomeEvent
    data class OnAddIncome(var data: Income) : IncomeEvent
    data class OnSelectedFilter(var filter: FilterPeriod) : IncomeEvent
}