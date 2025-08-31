package cd.zgeniuscoders.zwallet.modules.expense.presentation.ui.splash_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cd.zgeniuscoders.zwallet.core.navigation.Route
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier, navController: NavController) {
    val vm = hiltViewModel<SplashScreenViewModel>()
    var state = vm.state

    LaunchedEffect(state.isAuthenticated) {
        delay(5000)
        if (state.isAuthenticated) {
            navController.navigate(Route.MainPage) {
                popUpTo(Route.SplashScreen) { inclusive = true }
            }
        } else {
            navController.navigate(Route.LoginPage) {
                popUpTo(Route.SplashScreen) { inclusive = true }
            }
        }
    }

    SplashScreenBody(modifier)

}

@Composable
fun SplashScreenBody(modifier: Modifier) {
    Scaffold { innerP ->
        Column(
            modifier.padding(innerP).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("ZWallet Tracker", fontSize = 24.sp)
        }
    }
}