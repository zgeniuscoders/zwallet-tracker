package cd.zgeniuscoders.zwallet.auth.presentation.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import cd.zgeniuscoders.zwallet.core.navigation.Route

@Composable
fun RegisterPage(
    navController: NavController,
    snackBar: SnackbarHostState
) {
    var vm = hiltViewModel<RegisterViewModel>()
    var state = vm.state
    var onEvent = vm::onEvent


    LaunchedEffect(state.isRegistered) {
        if (state.isRegistered) {
            navController.navigate(Route.MainPage) {
                popUpTo(Route.LoginPage) { inclusive = true }
            }
        }
    }

    LaunchedEffect(state.errorMessage) {
        if (state.errorMessage.isNotBlank()) {
            snackBar.showSnackbar(state.errorMessage)
        }
    }

    RegisterBody(
        state,
        onEvent,
        navController,
        snackbarHostState = snackBar
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBody(
    state: RegisterState,
    onEvent: (event: RegisterEvent) -> Unit,
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(Route.LoginPage) },
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerP ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerP)
                .padding(8.dp),
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
                        text = "Créer un compte",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 32.dp)
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
                        value = state.username,
                        onValueChange = { onEvent(RegisterEvent.OnUsernameChange(it)) },
                        label = { Text("Nom complet") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { onEvent(RegisterEvent.OnEmailChange(it)) },
                        label = { Text("Email") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = state.password,
                        onValueChange = { onEvent(RegisterEvent.OnPasswordChange(it)) },
                        label = { Text("Mot de passe") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { onEvent(RegisterEvent.OnHiddenPassword) }) {
                                Icon(
                                    if (state.hiddenPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (state.hiddenPassword) "Masquer" else "Afficher"
                                )
                            }
                        },
                        visualTransformation = if (state.hiddenPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = state.confirmPassword,
                        onValueChange = { onEvent(RegisterEvent.OnConfirmPasswordChange(it)) },
                        label = { Text("Confirmer le mot de passe") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = {
                                onEvent(RegisterEvent.OnHiddenConfirmPassword)
                            }) {
                                Icon(
                                    if (state.hiddenConfirmPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (state.hiddenConfirmPassword) "Masquer" else "Afficher"
                                )
                            }
                        },
                        visualTransformation = if (state.hiddenConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = state.confirmPassword.isNotEmpty() && state.password != state.confirmPassword
                    )

                    if (state.confirmPassword.isNotEmpty() && state.password != state.confirmPassword) {
                        Text(
                            text = "Les mots de passe ne correspondent pas",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Button(
                        onClick = {
                            onEvent(RegisterEvent.OnRegister)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isLoading
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("S'inscrire")
                        }
                    }
                }
            }
        }

    }
}
