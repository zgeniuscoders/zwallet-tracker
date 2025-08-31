package cd.zgeniuscoders.zwallet.modules.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.zwallet.core.utils.Constant
import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.core.domains.services.LocalStorageService
import cd.zgeniuscoders.zwallet.core.domains.services.UserService
import cd.zgeniuscoders.zwallet.modules.auth.domains.models.Register
import cd.zgeniuscoders.zwallet.modules.auth.domains.models.User
import cd.zgeniuscoders.zwallet.modules.auth.domains.services.AuthenticationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    var authService: AuthenticationService,
    var userService: UserService,
    var localStorageService: LocalStorageService
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
            try {
                state = state.copy(isLoading = true, errorMessage = "")
                var data = Register(
                    email = state.email,
                    password = state.password,
                    username = state.username
                )
                authService.register(data)
                    .onEach { res ->
                        when (res) {
                            is Response.Error -> {
                                state = state.copy(
                                    isLoading = false,
                                    errorMessage = res.message.toString()
                                )
                            }

                            is Response.Success -> {
                                var user = res.data
                                if (user != null) {
                                    saveUser(user)
                                }
                            }
                        }
                    }.launchIn(viewModelScope)
            } catch (e: Exception) {
                state = state.copy(errorMessage = e.message.toString(), isLoading = false)
            }
        }
    }

    private fun saveUser(register: Register) {
        viewModelScope.launch {
            var data = User(
                id = register.uuid.toString(),
                username = register.username,
                email = register.username
            )
            userService
                .addUser(data)
                .onEach { res ->
                    state = when (res) {
                        is Response.Error -> state.copy(
                            errorMessage = res.message.toString(),
                            isLoading = false
                        )

                        is Response.Success -> {
                            localStorageService.add(data.id, Constant.USER_ID)
                            localStorageService.add(true, Constant.IS_AUTHENTICATED)
                            state.copy(isLoading = false, isRegistered = true)
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

}