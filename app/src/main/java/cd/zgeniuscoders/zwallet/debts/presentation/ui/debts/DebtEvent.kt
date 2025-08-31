package cd.zgeniuscoders.zwallet.debts.presentation.ui.debts

import cd.zgeniuscoders.zwallet.debts.domain.models.Debt

sealed interface DebtEvent {
    data object OnAddDebtDialog : DebtEvent
    data class OnAddDent(var data: Debt) : DebtEvent
    data class OnSelectedTab(var index: Int) : DebtEvent
}