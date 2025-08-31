package cd.zgeniuscoders.zwallet.modules.expense.domain.enums

import androidx.compose.ui.graphics.Color

enum class ExpenseCategory(val displayName: String, val color: Color) {
    NORMAL("Dépense normale", Color(0xFF2196F3)),
    AVOID("Dépense à éviter", Color(0xFFF44336)),
    USELESS("Dépense inutile", Color(0xFFFF9800))
}