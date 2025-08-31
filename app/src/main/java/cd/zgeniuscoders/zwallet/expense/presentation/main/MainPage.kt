package cd.zgeniuscoders.zwallet.expense.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.zwallet.core.navigation.Route
import cd.zgeniuscoders.zwallet.core.utils.fromRoute
import cd.zgeniuscoders.zwallet.expense.presentation.dashboard.DashboardPage
import cd.zgeniuscoders.zwallet.debts.presentation.ui.debts.DebtsPage
import cd.zgeniuscoders.zwallet.expense.presentation.expenses.ExpensesPage
import cd.zgeniuscoders.zwallet.income.presentation.ui.income.IncomePage
import cd.zgeniuscoders.zwallet.expense.presentation.profile.ProfilePage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

   navBackStackEntry.fromRoute()

    val bottomNavItems = listOf(
        BottomNavItem(Route.Dashboard, "Dashboard", Icons.Default.Dashboard),
        BottomNavItem(Route.Expenses, "Dépenses", Icons.Default.CreditCard),
        BottomNavItem(Route.Income, "Recettes", Icons.Default.TrendingUp),
        BottomNavItem(
            Route.Debts, " Dettes ", Icons.Default.AccountBalance
        ),
        BottomNavItem(
            Route.Profile, "Profil", Icons.Default.Person
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = navBackStackEntry.fromRoute() == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Dashboard,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Route.Dashboard> { DashboardPage() }
            composable<Route.Expenses> { ExpensesPage() }
            composable<Route.Income> { IncomePage() }
            composable<Route.Debts> { DebtsPage() }
            composable<Route.Profile> { ProfilePage() }
        }
    }
}

data class BottomNavItem(
    val route: Route,
    val label: String,
    val icon: ImageVector
)
