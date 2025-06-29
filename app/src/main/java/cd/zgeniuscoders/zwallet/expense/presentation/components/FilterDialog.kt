package cd.zgeniuscoders.zwallet.expense.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.zwallet.expense.domain.enums.FilterPeriod

@Composable
fun FilterDialog(
    currentFilter: FilterPeriod,
    onDismiss: () -> Unit,
    onFilterSelected: (FilterPeriod) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filtrer par période") },
        text = {
            Column {
                FilterPeriod.values().forEach { period ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentFilter == period,
                            onClick = { onFilterSelected(period) }
                        )
                        Text(
                            text = when (period) {
                                FilterPeriod.DAY -> "Aujourd'hui"
                                FilterPeriod.WEEK -> "Cette semaine"
                                FilterPeriod.MONTH -> "Ce mois"
                                FilterPeriod.YEAR -> "Cette année"
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Fermer")
            }
        }
    )
}