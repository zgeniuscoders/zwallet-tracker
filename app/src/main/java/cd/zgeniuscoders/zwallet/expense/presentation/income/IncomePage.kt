package cd.zgeniuscoders.zwallet.expense.presentation.income
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.zwallet.expense.domain.enums.FilterPeriod
import cd.zgeniuscoders.zwallet.expense.domain.models.Income
import cd.zgeniuscoders.zwallet.expense.presentation.expenses.FilterDialog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomePage() {
    var showAddIncomeDialog by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf(FilterPeriod.MONTH) }

    val incomes = remember {
        mutableStateListOf(
            Income("1", 2500.00, "Salaire", LocalDateTime.now().minusDays(1)),
            Income("2", 150.00, "Freelance", LocalDateTime.now().minusDays(3)),
            Income("3", 50.00, "Vente en ligne", LocalDateTime.now().minusDays(5)),
            Income("4", 200.00, "Prime", LocalDateTime.now().minusDays(10)),
            Income("5", 75.00, "Remboursement", LocalDateTime.now().minusDays(15))
        )
    }

    val totalIncome = incomes.sumOf { it.amount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recettes") },
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filtrer")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddIncomeDialog = true },
                containerColor = Color(0xFF4CAF50)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter une recette")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.TrendingUp,
                            contentDescription = null,
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(32.dp)
                        )

                        Column {
                            Text(
                                text = "Total des recettes",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "+${String.format("%.2f", totalIncome)} €",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4CAF50)
                            )
                            Text(
                                text = "Ce mois-ci",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            items(incomes) { income ->
                IncomeItem(income = income)
            }
        }
    }

    if (showAddIncomeDialog) {
        AddIncomeDialog(
            onDismiss = { showAddIncomeDialog = false },
            onConfirm = { newIncome ->
                incomes.add(0, newIncome.copy(id = System.currentTimeMillis().toString()))
                showAddIncomeDialog = false
            }
        )
    }

    if (showFilterDialog) {
        FilterDialog(
            currentFilter = selectedFilter,
            onDismiss = { showFilterDialog = false },
            onFilterSelected = { filter ->
                selectedFilter = filter
                showFilterDialog = false
            }
        )
    }
}

@Composable
fun IncomeItem(income: Income) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = income.description,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = income.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "+${String.format("%.2f", income.amount)} €",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
        }
    }
}

@Composable
fun AddIncomeDialog(
    onDismiss: () -> Unit,
    onConfirm: (Income) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ajouter une recette") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Montant (€)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (amount.isNotBlank() && description.isNotBlank()) {
                        onConfirm(
                            Income(
                                amount = amount.toDoubleOrNull() ?: 0.0,
                                description = description
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
