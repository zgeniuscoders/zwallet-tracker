package cd.zgeniuscoders.floapp.ui.screens.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.floapp.models.Login
import cd.zgeniuscoders.floapp.remote.AuthenticationService
import cd.zgeniuscoders.floapp.utilis.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private var authenticationService: AuthenticationService
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    fun onTriggerEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.OnLogin -> login()
            is LoginEvent.OnEmailChange -> state = state.copy(email = event.email)
            is LoginEvent.OnPasswordChange -> state = state.copy(password = event.password)
            LoginEvent.OnTogglePassword -> state =
                state.copy(isPasswordVisible = !state.isPasswordVisible)
        }
    }

    private fun login() {

        state = state.copy(isLoading = true)

        viewModelScope.launch {

            Log.i("FLOAPP", "clicked")


            authenticationService.login(
                Login(
                    state.email, state.password
                )
            ).onEach { res ->

                when (res) {
                    is Response.Error -> {
                        Log.e("FLOAPP", res.message.toString())

                        withContext(Dispatchers.Main) {
                            state = state.copy(
                                errorMessages = res.message.toString(),
                                isLoading = false
                            )
                        }
                    }

                    is Response.Success -> {
                        Log.i("FLOAPP", "logged")

                        withContext(Dispatchers.Main) {
                            state = state.copy(
                                isLoading = false, email = "", password = "", isLogged = true
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)


        }
    }
}