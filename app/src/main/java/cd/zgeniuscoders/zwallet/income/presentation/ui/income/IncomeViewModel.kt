package cd.zgeniuscoders.zwallet.income.presentation.ui.income

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.zwallet.core.utils.Constant
import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.income.domain.models.Income
import cd.zgeniuscoders.zwallet.expense.domain.services.IncomeService
import cd.zgeniuscoders.zwallet.income.presentation.mappers.toIncomeUiList
import cd.zgeniuscoders.zwallet.shared.domains.services.LocalStorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    var incomeService: IncomeService,
    var localStorageService: LocalStorageService
) : ViewModel() {

    var state by mutableStateOf(IncomeState())
        private set

    init {
        getIncomes()
    }

    fun onEvent(event: IncomeEvent) {
        when (event) {
            is IncomeEvent.OnAddIncome -> addIncome(event.data)
            IncomeEvent.OnShowAddIncomeDialog -> state =
                state.copy(showAddIncomeDialog = !state.showAddIncomeDialog)

            IncomeEvent.OnShowFilterDialog -> state =
                state.copy(showFilterDialog = !state.showFilterDialog)

            is IncomeEvent.OnSelectedFilter -> state = state.copy(selectedFilter = event.filter)
        }
    }

    fun getIncomes() {
        viewModelScope.launch {
            state = state.copy(isLoading = false, message = "")
            localStorageService.get<String>(Constant.USER_ID, "")
                .collect {
                    incomeService
                        .getIncomes(it)
                        .onEach { res ->
                            when (res) {
                                is Response.Error -> {
                                    state = state.copy(
                                        message = res.message.toString(),
                                        isLoading = false
                                    )
                                }

                                is Response.Success -> {
                                    val incomes = res.data
                                    if (incomes != null) {
                                        state = state.copy(incomes = incomes.toIncomeUiList())
                                    }
                                    state = state.copy(isLoading = false)
                                }
                            }
                        }
                        .launchIn(viewModelScope)
                }

        }
    }

    private fun addIncome(income: Income) {
        viewModelScope.launch {
            state = state.copy(message = "")
            localStorageService.get<String>(Constant.USER_ID, "")
                .collect { res ->
                    val date = LocalDateTime.now().toString()
                    val newData = income.copy(userId = res, date = date)
                    incomeService.addIncome(newData)
                        .onEach { res ->
                            state = when (res) {
                                is Response.Error -> {
                                    state.copy(message = res.message.toString())
                                }

                                is Response.Success -> {
                                    state.copy(message = "success")
                                }
                            }
                        }.launchIn(viewModelScope)
                }

        }
    }
}