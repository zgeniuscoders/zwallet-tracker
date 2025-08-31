package cd.zgeniuscoders.zwallet.debts.presentation.ui.debts

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cd.zgeniuscoders.zwallet.debts.presentation.ui.debts.components.AddDebtDialog
import cd.zgeniuscoders.zwallet.debts.presentation.ui.debts.components.DebtItem

@Composable
fun DebtsPage(modifier: Modifier = Modifier) {
    var vm = hiltViewModel<DebtViewModel>()
    var state = vm.state

    DebtsBody(state, onEvent = vm::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtsBody(state: DebtState, onEvent: (DebtEvent) -> Unit) {
    var showAddDebtDialog = state.showAddDebtDialog
    var selectedTab = state.selectedTab

    val myDebts = state.myDebts
    val othersDebts = state.othersDebts
    val totalMyDebts = myDebts.sumOf { it.amount }
    val totalOthersDebts = othersDebts.sumOf { it.amount }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Dettes") })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { onEvent(DebtEvent.OnAddDebtDialog) }, containerColor = Color(0xFFFF9800)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Ajouter une dette")
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

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
                    onClick = { onEvent(DebtEvent.OnSelectedTab(0)) },
                    text = { Text("Je dois (${myDebts.size})") })
                Tab(
                    selected = selectedTab == 1,
                    onClick = { onEvent(DebtEvent.OnSelectedTab(1)) },
                    text = { Text("On me doit (${othersDebts.size})") })
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
                    DebtItem(debt = debt, onMarkAsPaid = {
                        // Marquer comme payé
                    }, onSetReminder = {
                        // Définir un rappel
                    })
                }
            }
        }
    }

    if (showAddDebtDialog) {
        AddDebtDialog(onDismiss = { onEvent(DebtEvent.OnAddDebtDialog) }, onConfirm = { newDebt ->
            onEvent(DebtEvent.OnAddDent(newDebt))
            onEvent(DebtEvent.OnAddDebtDialog)
        })
    }
}



