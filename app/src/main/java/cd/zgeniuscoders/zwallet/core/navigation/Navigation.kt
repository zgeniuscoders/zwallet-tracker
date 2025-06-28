package cd.zgeniuscoders.zwallet.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.zwallet.auth.presentation.forgot_password.ForgotPasswordPage
import cd.zgeniuscoders.zwallet.auth.presentation.login.LoginPage
import cd.zgeniuscoders.zwallet.auth.presentation.register.RegisterPage
import cd.zgeniuscoders.zwallet.expense.presentation.main.MainPage

@Composable
fun Navigation(
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.LoginPage
    ) {
        composable<Route.LoginPage> {
            LoginPage(
                onNavigateToRegister = { navController.navigate(Route.RegisterPage) },
                onNavigateToForgotPassword = { navController.navigate(Route.ForgotPasswordPage) },
                onNavigateToMain = {
                    navController.navigate(Route.MainPage) {
                        popUpTo(Route.LoginPage) { inclusive = true }
                    }
                }
            )
        }

        composable<Route.RegisterPage> {
            RegisterPage(
                onNavigateToLogin = { navController.popBackStack() },
                onNavigateToMain = {
                    navController.navigate(Route.MainPage) {
                        popUpTo(Route.LoginPage) { inclusive = true }
                    }
                }
            )
        }

        composable<Route.ForgotPasswordPage> {
            ForgotPasswordPage(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable<Route.MainPage> {
            MainPage()
        }
    }
}
