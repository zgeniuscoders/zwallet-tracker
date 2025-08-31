package cd.zgeniuscoders.zwallet.modules.debts.presentation.mappers

import cd.zgeniuscoders.zwallet.core.utils.formatDate
import cd.zgeniuscoders.zwallet.core.utils.toLocalDate
import cd.zgeniuscoders.zwallet.modules.debts.domain.models.Debt
import cd.zgeniuscoders.zwallet.modules.debts.presentation.models.DebtUi
import java.time.LocalDateTime

fun List<Debt>.toDebtUiModel(): List<DebtUi> {
    return this.map {

        DebtUi(
            id = it.id,
            week = it.dueDate.toLocalDate().isBefore(LocalDateTime.now().plusDays(7)),
            amount = it.amount,
            description = it.description,
            isOwedByMe = it.isOwedByMe,
            date = it.dueDate.formatDate() ?: "",
            isOverdue = it.dueDate.toLocalDate().isBefore(LocalDateTime.now()),
            creditor = it.creditorName,
            amountFormat = "${it.amount} CDF",
        )
    }
}