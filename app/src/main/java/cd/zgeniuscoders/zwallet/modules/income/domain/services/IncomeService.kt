package cd.zgeniuscoders.zwallet.modules.income.domain.services

import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.modules.income.domain.models.Income
import kotlinx.coroutines.flow.Flow

interface IncomeService {

    fun getIncomes(userId: String): Flow<Response<List<Income>>>

    fun addIncome(data: Income): Flow<Response<Boolean>>

}