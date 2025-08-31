package cd.zgeniuscoders.zwallet.modules.expense.presentation.models

import androidx.compose.ui.graphics.Color

data class ExpenseUi(
    var description: String,
    var observation: String,
    var date: String,
    var amountFormat: String,
    var amount: Double,
    var category: String,
    var color: Color
)
