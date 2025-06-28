package cd.zgeniuscoders.zwallet.domain.enums

enum class ExpenseCategory(val displayName: String, val color: androidx.compose.ui.graphics.Color) {
    NORMAL("Dépense normale", androidx.compose.ui.graphics.Color(0xFF2196F3)),
    AVOID("Dépense à éviter", androidx.compose.ui.graphics.Color(0xFFF44336)),
    USELESS("Dépense inutile", androidx.compose.ui.graphics.Color(0xFFFF9800))
}