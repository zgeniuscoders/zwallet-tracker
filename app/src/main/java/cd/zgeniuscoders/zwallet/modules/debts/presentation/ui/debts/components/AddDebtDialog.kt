package cd.zgeniuscoders.zwallet.modules.debts.presentation.ui.debts.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.zwallet.modules.debts.domain.models.Debt
import java.time.LocalDateTime

@Composable
fun AddDebtDialog(
    onDismiss: () -> Unit,
    onConfirm: (Debt) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var creditorName by remember { mutableStateOf("") }
    var isOwedByMe by remember { mutableStateOf(true) }
    var dueDate by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ajouter une dette") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        onClick = { isOwedByMe = true },
                        label = { Text("Je dois") },
                        selected = isOwedByMe,
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        onClick = { isOwedByMe = false },
                        label = { Text("On me doit") },
                        selected = !isOwedByMe,
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Montant (CDF)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = creditorName,
                    onValueChange = { creditorName = it },
                    label = { Text(if (isOwedByMe) "Créancier" else "Débiteur") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("Date d'échéance (JJ/MM/AAAA)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (amount.isNotBlank() && description.isNotBlank() && creditorName.isNotBlank()) {
                        onConfirm(
                            Debt(
                                amount = amount.toDoubleOrNull() ?: 0.0,
                                description = description,
                                creditorName = creditorName,
                                isOwedByMe = isOwedByMe,
                                dueDate = LocalDateTime.now().toString()
                            )
                        )
                    }
                }
            ) {
                Text("Ajouter")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}
