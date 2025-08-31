package cd.zgeniuscoders.zwallet.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.zwallet.auth.domains.models.Login
import cd.zgeniuscoders.zwallet.auth.domains.services.AuthenticationService
import cd.zgeniuscoders.zwallet.core.utils.Constant
import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.shared.domains.services.LocalStorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private var authenticationService: AuthenticationService,
    private var localStorageService: LocalStorageService
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.OnLogin -> login()
            is LoginEvent.OnEmailChange -> state = state.copy(email = event.email)
            is LoginEvent.OnPasswordChange -> state = state.copy(password = event.password)
            LoginEvent.OnTogglePassword -> state =
                state.copy(isPasswordVisible = !state.isPasswordVisible)
        }
    }

    private fun login() {

        state = state.copy(isLoading = true, errorMessages = "")

        viewModelScope.launch {
            val data = Login(state.email, state.password)
            authenticationService.login(data)
                .onEach { res ->

                    when (res) {
                        is Response.Error -> {
                            withContext(Dispatchers.Main) {
                                state = state.copy(
                                    errorMessages = res.message.toString(),
                                    isLoading = false
                                )
                            }
                        }

                        is Response.Success -> {
                            withContext(Dispatchers.Main) {
                                localStorageService.add(res.data.toString(), Constant.USER_ID)
                                localStorageService.add(true, Constant.IS_AUTHENTICATED)
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