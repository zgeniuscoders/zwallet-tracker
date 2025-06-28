package cd.zgeniuscoders.zwallet.expense.presentation.dashboard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cd.zgeniuscoders.zwallet.expense.domain.enums.ExpenseCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardPage() {
    var animationPlayed by remember { mutableStateOf(false) }
    val animatedProgress by animateFloatAsState(
        targetValue = if (animationPlayed) 1f else 0f,
        animationSpec = tween(1000),
        label = "progress"
    )

    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    listOf(
                        StatCard("Dépenses totales", "2,450 €", Icons.Default.CreditCard, Color(0xFFE57373)),
                        StatCard("Recettes totales", "3,200 €", Icons.Default.TrendingUp, Color(0xFF81C784)),
                        StatCard("Dettes", "850 €", Icons.Default.AccountBalance, Color(0xFFFFB74D)),
                        StatCard("Solde", "750 €", Icons.Default.TrendingDown, Color(0xFF64B5F6))
                    )
                ) { card ->
                    StatCardItem(card, animatedProgress)
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Répartition des dépenses",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    ExpensePieChart(
                        data = listOf(
                            PieChartData("Normales", 60f, ExpenseCategory.NORMAL.color),
                            PieChartData("À éviter", 25f, ExpenseCategory.AVOID.color),
                            PieChartData("Inutiles", 15f, ExpenseCategory.USELESS.color)
                        ),
                        animatedProgress = animatedProgress
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Transactions récentes",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    repeat(5) { index ->
                        RecentTransactionItem(
                            title = "Transaction ${index + 1}",
                            amount = "${(50..200).random()} €",
                            isExpense = index % 2 == 0
                        )
                        if (index < 4) {
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatCardItem(card: StatCard, animatedProgress: Float) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = card.color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = card.icon,
                contentDescription = null,
                tint = card.color,
                modifier = Modifier.size(24.dp)
            )
            
            Column {
                Text(
                    text = card.title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = card.value,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = card.color
                )
            }
        }
    }
}

@Composable
fun ExpensePieChart(
    data: List<PieChartData>,
    animatedProgress: Float,
    modifier: Modifier = Modifier
) {
    val total = data.sumOf { it.value.toDouble() }.toFloat()
    
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier.size(120.dp)
        ) {
            var startAngle = -90f
            data.forEach { item ->
                val sweepAngle = (item.value / total) * 360f * animatedProgress
                drawArc(
                    color = item.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    size = Size(size.width * 0.8f, size.height * 0.8f),
                    topLeft = Offset(size.width * 0.1f, size.height * 0.1f)
                )
                startAngle += sweepAngle
            }
        }
        
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            data.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .drawBehind { drawRect(item.color) }
                    )
                    Text(
                        text = "${item.label}: ${item.value.toInt()}%",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun RecentTransactionItem(
    title: String,
    amount: String,
    isExpense: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Aujourd'hui",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Text(
            text = if (isExpense) "-$amount" else "+$amount",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = if (isExpense) Color(0xFFE57373) else Color(0xFF81C784)
        )
    }
}

data class StatCard(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val color: Color
)

data class PieChartData(
    val label: String,
    val value: Float,
    val color: Color
)
