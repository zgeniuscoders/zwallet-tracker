package cd.zgeniuscoders.zwallet.debts.presentation.ui.debts

import cd.zgeniuscoders.zwallet.debts.presentation.models.DebtUi

data class DebtState(
    var isLoading: Boolean = false,
    var message: String = "",
    var myDebts: List<DebtUi> = emptyList(),
    var othersDebts: List<DebtUi> = emptyList(),
    var showAddDebtDialog: Boolean = false,
    var selectedTab: Int = 0
)
