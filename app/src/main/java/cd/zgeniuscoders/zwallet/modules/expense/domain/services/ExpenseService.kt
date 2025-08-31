package cd.zgeniuscoders.zwallet.modules.expense.domain.services

import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.modules.expense.domain.models.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseService {

    fun getExpenses(userId: String): Flow<Response<List<Expense>>>

    fun addExpense(data: Expense): Flow<Response<Boolean>>

}