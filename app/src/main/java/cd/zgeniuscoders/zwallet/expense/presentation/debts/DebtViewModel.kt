package cd.zgeniuscoders.zwallet.expense.presentation.debts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.zwallet.core.utils.Constant
import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.expense.domain.models.Debt
import cd.zgeniuscoders.zwallet.expense.domain.services.DebtService
import cd.zgeniuscoders.zwallet.shared.domains.services.LocalStorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebtViewModel @Inject constructor(
    var debtService: DebtService,
    var localStorageService: LocalStorageService
) : ViewModel() {

    var state by mutableStateOf(DebtState())
        private set

    init {
        getDebts()
    }

    fun onEvent(event: DebtEvent) {
        when (event) {
            DebtEvent.OnAddDebtDialog -> state =
                state.copy(showAddDebtDialog = !state.showAddDebtDialog)

            is DebtEvent.OnAddDent -> addDebt(event.data)
            is DebtEvent.OnSelectedTab -> state = state.copy(selectedTab = event.index)
        }
    }

    private fun addDebt(debt: Debt) {
        viewModelScope.launch {
            localStorageService
                .get<String>(Constant.USER_ID, "")
                .collect {
                    var newDebt = debt.copy(userId = it)
                    debtService
                        .addDebt(newDebt)
                        .onEach { res ->
                            state = when (res) {
                                is Response.Error -> {
                                    state.copy(message = res.message.toString())
                                }

                                is Response.Success -> {
                                    state.copy(message = "suceess")
                                }
                            }
                        }.launchIn(viewModelScope)
                }

        }
    }

    fun getDebts() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, message = "")
            localStorageService
                .get<String>(Constant.USER_ID, "")
                .collect {
                    debtService
                        .getDebts(it)
                        .onEach { res ->

                            when (res) {
                                is Response.Error -> {
                                    state = state.copy(
                                        message = res.message.toString(),
                                        isLoading = false
                                    )
                                }

                                is Response.Success -> {
                                    var debts = res.data

                                    if (debts != null) {

                                        var myDebts = debts.filter { it.isOwedByMe && !it.isPaid }
                                        val othersDebts =
                                            debts.filter { !it.isOwedByMe && !it.isPaid }

                                        state =
                                            state.copy(
                                                myDebts = myDebts,
                                                othersDebts = othersDebts,
                                                isLoading = false
                                            )
                                    }

                                }
                            }

                        }
                        .launchIn(viewModelScope)
                }
        }
    }

}