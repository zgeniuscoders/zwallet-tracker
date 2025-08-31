package cd.zgeniuscoders.zwallet.core.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cd.zgeniuscoders.zwallet.modules.auth.presentation.forgot_password.ForgotPasswordPage
import cd.zgeniuscoders.zwallet.modules.auth.presentation.login.LoginPage
import cd.zgeniuscoders.zwallet.modules.auth.presentation.register.RegisterPage
import cd.zgeniuscoders.zwallet.modules.expense.presentation.ui.main.MainPage
import cd.zgeniuscoders.zwallet.modules.expense.presentation.ui.splash_screen.SplashScreen

@Composable
fun Navigation(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    snackBar: SnackbarHostState = SnackbarHostState()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.SplashScreen
    ) {
        composable<Route.SplashScreen> {
            SplashScreen(
                navController = navController
            )
        }

        composable<Route.LoginPage> {
            LoginPage(
                navController = navController,
                snackbarHostState = snackBar
            )
        }

        composable<Route.RegisterPage> {
            RegisterPage(
                navController = navController,
                snackBar = snackBar
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
