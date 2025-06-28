package cd.zgeniuscoders.zwallet.expense.presentation.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.zwallet.expense.domain.enums.ExpenseCategory
import cd.zgeniuscoders.zwallet.expense.domain.enums.FilterPeriod
import cd.zgeniuscoders.zwallet.expense.domain.models.Expense
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesPage() {
    var showAddExpenseDialog by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf(FilterPeriod.MONTH) }

    val expenses = remember {
        listOf(
            Expense("1", 45.50, "Courses alimentaires", "Supermarché Carrefour", ExpenseCategory.NORMAL),
            Expense("2", 120.00, "Restaurant", "Dîner avec amis", ExpenseCategory.AVOID),
            Expense("3", 25.99, "Application mobile", "Abonnement inutile", ExpenseCategory.USELESS),
            Expense("4", 80.00, "Essence", "", ExpenseCategory.NORMAL),
            Expense("5", 15.50, "Café", "Starbucks", ExpenseCategory.AVOID)
        )
    }

    val totalExpenses = expenses.sumOf { it.amount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dépenses") },
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filtrer")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddExpenseDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter une dépense")
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
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Total des dépenses",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${String.format("%.2f", totalExpenses)} €",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Ce mois-ci",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            items(expenses) { expense ->
                ExpenseItem(expense = expense)
            }
        }
    }

    if (showAddExpenseDialog) {
        AddExpenseDialog(
            onDismiss = { showAddExpenseDialog = false },
            onConfirm = { 
                // Ajouter la dépense
                showAddExpenseDialog = false 
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
fun ExpenseItem(expense: Expense) {
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(expense.category.color)
                )
                
                Column {
                    Text(
                        text = expense.description,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    if (expense.observation.isNotEmpty()) {
                        Text(
                            text = expense.observation,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = expense.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "-${String.format("%.2f", expense.amount)} €",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = expense.category.color
                )
                Text(
                    text = expense.category.displayName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun AddExpenseDialog(
    onDismiss: () -> Unit,
    onConfirm: (Expense) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var observation by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(ExpenseCategory.NORMAL) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ajouter une dépense") },
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
                
                OutlinedTextField(
                    value = observation,
                    onValueChange = { observation = it },
                    label = { Text("Observation (optionnel)") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Text(
                    text = "Catégorie",
                    style = MaterialTheme.typography.labelMedium
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ExpenseCategory.values().forEach { category ->
                        FilterChip(
                            onClick = { selectedCategory = category },
                            label = { Text(category.displayName) },
                            selected = selectedCategory == category,
                            leadingIcon = {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(category.color)
                                )
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (amount.isNotBlank() && description.isNotBlank()) {
                        onConfirm(
                            Expense(
                                amount = amount.toDoubleOrNull() ?: 0.0,
                                description = description,
                                observation = observation,
                                category = selectedCategory
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
