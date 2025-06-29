package cd.zgeniuscoders.zwallet.expense.presentation.debts

import cd.zgeniuscoders.zwallet.expense.domain.models.Debt

sealed interface DebtEvent {
    data object OnAddDebtDialog : DebtEvent
    data class OnAddDent(var data: Debt) : DebtEvent
    data class OnSelectedTab(var index: Int) : DebtEvent
}