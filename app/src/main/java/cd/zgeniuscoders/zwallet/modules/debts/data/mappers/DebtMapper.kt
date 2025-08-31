package cd.zgeniuscoders.zwallet.modules.debts.data.mappers

import cd.zgeniuscoders.zwallet.modules.debts.data.network.DebtDto
import cd.zgeniuscoders.zwallet.modules.debts.domain.models.Debt


fun List<DebtDto>.toDebtDomainList(): List<Debt> {
    return map {
        Debt(
            id = it.id,
            userId = it.userId,
            amount = it.amount,
            description = it.description,
            dueDate = it.dueDate,
            creditorName = it.creditorName,
            isOwedByMe = it.owedByMe,
            isPaid = it.paid
        )
    }
}