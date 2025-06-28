package cd.zgeniuscoders.zwallet.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.zwallet.auth.domains.models.Register
import cd.zgeniuscoders.zwallet.auth.domains.services.AuthenticationService
import cd.zgeniuscoders.zwallet.core.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    var authService: AuthenticationService
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnConfirmPasswordChange -> state =
                state.copy(confirmPassword = event.password)

            is RegisterEvent.OnEmailChange -> state = state.copy(email = event.email)
            RegisterEvent.OnHiddenConfirmPassword -> state =
                state.copy(hiddenConfirmPassword = !state.hiddenConfirmPassword)

            RegisterEvent.OnHiddenPassword -> state =
                state.copy(hiddenPassword = !state.hiddenPassword)

            is RegisterEvent.OnPasswordChange -> state = state.copy(password = event.password)
            RegisterEvent.OnRegister -> register()
            is RegisterEvent.OnUsernameChange -> state = state.copy(username = event.name)
        }
    }

    fun register() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            var data = Register(
                email = state.email,
                password = state.password,
                username = state.username
            )
            authService.register(data)
                .onEach { res ->
                    state = when (res) {
                        is Response.Error -> {
                            state.copy(isLoading = false, errorMessage = res.message.toString())
                        }

                        is Response.Success -> {
                            state.copy(isLoading = false, isRegistered = true)
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

}