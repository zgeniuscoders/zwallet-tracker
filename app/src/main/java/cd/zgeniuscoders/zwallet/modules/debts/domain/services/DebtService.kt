package cd.zgeniuscoders.zwallet.modules.debts.domain.services

import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.modules.debts.domain.models.Debt
import kotlinx.coroutines.flow.Flow

interface DebtService {

    fun getDebts(userId: String): Flow<Response<List<Debt>>>

    fun addDebt(data: Debt): Flow<Response<Boolean>>

}