package cd.zgeniuscoders.zwallet.income.presentation.ui.income

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
import androidx.hilt.navigation.compose.hiltViewModel
import cd.zgeniuscoders.zwallet.expense.presentation.components.FilterDialog
import cd.zgeniuscoders.zwallet.income.presentation.ui.income.components.AddIncomeDialog
import cd.zgeniuscoders.zwallet.income.presentation.ui.income.components.IncomeItem

@Composable
fun IncomePage(modifier: Modifier = Modifier) {
    val vm = hiltViewModel<IncomeViewModel>()
    val state = vm.state

    IncomeBody(
        state,
        vm::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeBody(state: IncomeState, onEvent: (IncomeEvent) -> Unit) {
    var showAddIncomeDialog = state.showAddIncomeDialog
    val showFilterDialog = state.showFilterDialog
    val selectedFilter = state.selectedFilter

    val incomes = state.incomes

    val totalIncome = incomes.sumOf { it.amount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recettes") },
                actions = {
                    IconButton(onClick = { onEvent(IncomeEvent.OnShowFilterDialog) }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filtrer")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(IncomeEvent.OnShowAddIncomeDialog) },
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
                                text = "+${String.format("%.2f", totalIncome)} CDF",
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
            onDismiss = { onEvent(IncomeEvent.OnShowAddIncomeDialog) },
            onConfirm = { newIncome ->
                onEvent(IncomeEvent.OnAddIncome(newIncome))
                onEvent(IncomeEvent.OnShowAddIncomeDialog)
            }
        )
    }

    if (showFilterDialog) {
        FilterDialog(
            currentFilter = selectedFilter,
            onDismiss = { onEvent(IncomeEvent.OnShowFilterDialog) },
            onFilterSelected = { filter ->
                onEvent(IncomeEvent.OnSelectedFilter(filter))
                onEvent(IncomeEvent.OnShowFilterDialog)
            }
        )
    }
}



