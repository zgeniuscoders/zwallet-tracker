package cd.zgeniuscoders.zwallet.auth.presentation.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cd.zgeniuscoders.zwallet.auth.domains.models.Login
import cd.zgeniuscoders.zwallet.core.navigation.Route

@Composable
fun LoginPage(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {

    val vm = hiltViewModel<LoginViewModel>()
    val state = vm.state

    LaunchedEffect(state.isLogged) {
        if (state.isLogged) {
            navController.navigate(Route.MainPage) {
                popUpTo(Route.LoginPage) { inclusive = true }
            }
        }
    }

    LaunchedEffect(state.errorMessages) {
        if (state.errorMessages.isNotBlank()) {
            snackbarHostState.showSnackbar(message = state.errorMessages)
        }
    }

    LoginBody(
        vm::onEvent, vm.state, navController = navController, snackbarHostState = snackbarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginBody(
    onEvent: (event: LoginEvent) -> Unit,
    state: LoginState,
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    var email = state.email
    var password = state.password
    var passwordVisible = state.isPasswordVisible
    var isLoading = state.isLoading

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerP ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerP)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "💰",
                        fontSize = 64.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "ZWallet Tracker",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Gérez vos finances intelligemment",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { onEvent(LoginEvent.OnEmailChange(it)) },
                        label = { Text("Email") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { onEvent(LoginEvent.OnPasswordChange(it)) },
                        label = { Text("Mot de passe") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { onEvent(LoginEvent.OnTogglePassword) }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (passwordVisible) "Masquer" else "Afficher"
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    TextButton(
                        onClick = {
                            navController.navigate(Route.ForgotPasswordPage)
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Mot de passe oublié ?")
                    }

                    Button(
                        onClick = {
                            onEvent(LoginEvent.OnLogin)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = email.isNotBlank() && password.isNotBlank() && !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("Se connecter")
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Pas de compte ? ")
                        TextButton(onClick = {
                            navController.navigate(Route.RegisterPage)
                        }) {
                            Text("S'inscrire")
                        }
                    }
                }
            }
        }
    }


}
