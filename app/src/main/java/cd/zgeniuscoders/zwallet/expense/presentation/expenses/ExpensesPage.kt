package cd.zgeniuscoders.zwallet.expense.presentation.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cd.zgeniuscoders.zwallet.expense.domain.enums.FilterPeriod
import cd.zgeniuscoders.zwallet.expense.presentation.expenses.components.AddExpenseDialog
import cd.zgeniuscoders.zwallet.expense.presentation.expenses.components.ExpenseItem
import cd.zgeniuscoders.zwallet.expense.presentation.components.FilterDialog

@Composable
fun ExpensesPage() {
    var vm = hiltViewModel<ExpensesViewModel>()
    var state = vm.state
    var onEvent = vm::onEvent

    ExpensesBody(state, onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesBody(state: ExpensesState, onEvent: (ExpensesEvent) -> Unit) {
    var showAddExpenseDialog = state.showAddExpenseDialog
    var showFilterDialog = state.showFilterDialog
    var selectedFilter by remember { mutableStateOf(FilterPeriod.MONTH) }

    val expenses = state.expenses

    val totalExpenses = expenses.sumOf { it.amount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dépenses") },
                actions = {
                    IconButton(onClick = { onEvent(ExpensesEvent.OnFilterExpense) }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filtrer")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ExpensesEvent.OnAddExpense) }
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
            onDismiss = { onEvent(ExpensesEvent.OnAddExpense) },
            onConfirm = {
                onEvent(ExpensesEvent.AddExpense(it))
                onEvent(ExpensesEvent.OnAddExpense)
            }
        )
    }

    if (showFilterDialog) {
        FilterDialog(
            currentFilter = selectedFilter,
            onDismiss = { onEvent(ExpensesEvent.OnFilterExpense) },
            onFilterSelected = { filter ->
                selectedFilter = filter
                onEvent(ExpensesEvent.OnFilterExpense)
            }
        )
    }
}




