package cd.zgeniuscoders.zwallet.auth.presentation.login

sealed interface LoginEvent {

    data object OnLogin : LoginEvent

    data class OnEmailChange(var email: String) : LoginEvent
    data class OnPasswordChange(var password: String) : LoginEvent

    data object OnTogglePassword : LoginEvent

}