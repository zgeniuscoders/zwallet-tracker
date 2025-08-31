package cd.zgeniuscoders.zwallet.expense.presentation.expenses

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.zwallet.core.utils.Constant
import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.expense.domain.models.Expense
import cd.zgeniuscoders.zwallet.expense.domain.services.ExpenseService
import cd.zgeniuscoders.zwallet.expense.presentation.mappers.toExpenseUiModel
import cd.zgeniuscoders.zwallet.shared.domains.services.LocalStorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    var expenseService: ExpenseService,
    var localStorageService: LocalStorageService
) : ViewModel() {

    var state by mutableStateOf(ExpensesState())
        private set

    init {
        getExpenses()
    }

    fun onEvent(event: ExpensesEvent) {
        when (event) {
            ExpensesEvent.OnAddExpense -> state =
                state.copy(showAddExpenseDialog = !state.showAddExpenseDialog)

            ExpensesEvent.OnFilterExpense -> state =
                state.copy(showFilterDialog = !state.showFilterDialog)

            is ExpensesEvent.AddExpense -> addExpense(event.data)
        }
    }

    fun getExpenses() {
        viewModelScope.launch {
            state = state.copy(isLoading = false, message = "")
            localStorageService.get<String>(Constant.USER_ID, "")
                .collect {
                    expenseService
                        .getExpenses(it)
                        .onEach { res ->
                            when (res) {
                                is Response.Error -> {
                                    state = state.copy(
                                        message = res.message.toString(),
                                        isLoading = false
                                    )
                                }

                                is Response.Success -> {
                                    val expenses = res.data
                                    if (expenses != null) {
                                        state = state.copy(expenses = expenses.toExpenseUiModel())
                                    }
                                    state = state.copy(isLoading = false)
                                }
                            }
                        }
                        .launchIn(viewModelScope)
                }

        }
    }

    fun addExpense(data: Expense) {
        viewModelScope.launch {
            state = state.copy(message = "")
            localStorageService.get<String>(Constant.USER_ID, "")
                .collect { res ->
                    var date = LocalDateTime.now().toString()
                    val newData = data.copy(userId = res, date = date)
                    expenseService.addExpense(newData)
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