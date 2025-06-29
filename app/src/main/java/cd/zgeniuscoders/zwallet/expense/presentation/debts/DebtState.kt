package cd.zgeniuscoders.zwallet.expense.presentation.debts

import cd.zgeniuscoders.zwallet.expense.domain.models.Debt

data class DebtState(
    var isLoading: Boolean = false,
    var message: String = "",
    var myDebts: List<Debt> = emptyList(),
    var othersDebts: List<Debt> = emptyList(),
    var showAddDebtDialog: Boolean = false,
    var selectedTab: Int = 0
)
