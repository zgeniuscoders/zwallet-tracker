package cd.zgeniuscoders.zwallet.expense.domain.services

import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.expense.domain.models.Debt
import cd.zgeniuscoders.zwallet.expense.domain.models.Expense
import kotlinx.coroutines.flow.Flow

interface DebtService {

    fun getDebts(userId: String): Flow<Response<List<Debt>>>

    fun addDebt(data: Debt): Flow<Response<Boolean>>

}