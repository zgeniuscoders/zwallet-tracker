package cd.zgeniuscoders.zwallet.modules.income.presentation.mappers

import cd.zgeniuscoders.zwallet.core.utils.formatDate
import cd.zgeniuscoders.zwallet.modules.income.domain.models.Income
import cd.zgeniuscoders.zwallet.modules.income.presentation.models.IncomeUi

fun List<Income>.toIncomeUiList(): List<IncomeUi> {
    return map {
        IncomeUi(
            date = it.date.formatDate() ?: "",
            description = it.description,
            amount = it.amount,
            amountFormat = "${it.amount} CDF"
        )
    }
}