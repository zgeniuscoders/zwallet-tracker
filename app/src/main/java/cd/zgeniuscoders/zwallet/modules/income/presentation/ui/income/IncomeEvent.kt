package cd.zgeniuscoders.zwallet.modules.income.presentation.ui.income

import cd.zgeniuscoders.zwallet.modules.expense.domain.enums.FilterPeriod
import cd.zgeniuscoders.zwallet.modules.income.domain.models.Income

sealed interface IncomeEvent {
    data object OnShowAddIncomeDialog : IncomeEvent
    data object OnShowFilterDialog : IncomeEvent
    data class OnAddIncome(var data: Income) : IncomeEvent
    data class OnSelectedFilter(var filter: FilterPeriod) : IncomeEvent
}