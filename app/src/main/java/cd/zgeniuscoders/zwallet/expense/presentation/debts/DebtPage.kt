package cd.zgeniuscoders.zwallet.expense.presentation.debts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.zwallet.expense.domain.models.Debt
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtsPage() {
    var showAddDebtDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }

    val debts = remember {
        mutableStateListOf(
            Debt("1", 500.00, "Prêt personnel", "Marie Dupont", LocalDateTime.now().plusDays(15), true, false),
            Debt("2", 200.00, "Dîner restaurant", "Pierre Martin", LocalDateTime.now().plusDays(7), false, false),
            Debt("3", 1000.00, "Prêt voiture", "Banque", LocalDateTime.now().plusDays(30), true, false),
            Debt("4", 50.00, "Courses", "Sophie Leroy", LocalDateTime.now().plusDays(3), false, false),
            Debt("5", 300.00, "Réparation", "Jean Moreau", LocalDateTime.now().minusDays(2), true, false)
        )
    }

    val myDebts = debts.filter { it.isOwedByMe && !it.isPaid }
    val othersDebts = debts.filter { !it.isOwedByMe && !it.isPaid }
    val totalMyDebts = myDebts.sumOf { it.amount }
    val totalOthersDebts = othersDebts.sumOf { it.amount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dettes") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDebtDialog = true },
                containerColor = Color(0xFFFF9800)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter une dette")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Résumé des dettes
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCDD2))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Je dois",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFD32F2F)
                        )
                        Text(
                            text = "${String.format("%.2f", totalMyDebts)} €",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD32F2F)
                        )
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "On me doit",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF388E3C)
                        )
                        Text(
                            text = "${String.format("%.2f", totalOthersDebts)} €",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF388E3C)
                        )
                    }
                }
            }

            // Onglets
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Je dois (${myDebts.size})") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("On me doit (${othersDebts.size})") }
                )
            }

            // Liste des dettes
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                val currentDebts = if (selectedTab == 0) myDebts else othersDebts

                items(currentDebts) { debt ->
                    DebtItem(
                        debt = debt,
                        onMarkAsPaid = {
                            // Marquer comme payé
                        },
                        onSetReminder = {
                            // Définir un rappel
                        }
                    )
                }
            }
        }
    }

    if (showAddDebtDialog) {
        AddDebtDialog(
            onDismiss = { showAddDebtDialog = false },
            onConfirm = { newDebt ->
                debts.add(0, newDebt.copy(id = System.currentTimeMillis().toString()))
                showAddDebtDialog = false
            }
        )
    }
}

@Composable
fun DebtItem(
    debt: Debt,
    onMarkAsPaid: () -> Unit,
    onSetReminder: () -> Unit
) {
    val isOverdue = debt.dueDate.isBefore(LocalDateTime.now())
    val cardColor = when {
        isOverdue -> Color(0xFFFFEBEE)
        debt.dueDate.isBefore(LocalDateTime.now().plusDays(7)) -> Color(0xFFFFF3E0)
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                if (debt.isOwedByMe) Color(0xFFFFCDD2) else Color(0xFFC8E6C9)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (debt.isOwedByMe) Icons.Default.PersonAdd else Icons.Default.Person,
                            contentDescription = null,
                            tint = if (debt.isOwedByMe) Color(0xFFD32F2F) else Color(0xFF388E3C)
                        )
                    }

                    Column {
                        Text(
                            text = debt.creditorName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = debt.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "${String.format("%.2f", debt.amount)} €",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (debt.isOwedByMe) Color(0xFFD32F2F) else Color(0xFF388E3C)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (isOverdue) {
                            Icon(
                                imageVector = Icons.Default.Alarm,
                                contentDescription = null,
                                tint = Color(0xFFD32F2F),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = debt.dueDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isOverdue) Color(0xFFD32F2F) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onSetReminder,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Alarm,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Rappel")
                }

                Button(
                    onClick = onMarkAsPaid,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Payé")
                }
            }
        }
    }
}

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
                    label = { Text("Montant (€)") },
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
                                dueDate = LocalDateTime.now().plusDays(30) // Valeur par défaut
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
