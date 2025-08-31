package cd.zgeniuscoders.zwallet.modules.auth.presentation.register

sealed interface RegisterEvent {

    data class OnPasswordChange(var password: String) : RegisterEvent
    data class OnConfirmPasswordChange(var password: String) : RegisterEvent
    data class OnEmailChange(var email: String) : RegisterEvent
    data class OnUsernameChange(var name: String) : RegisterEvent

    data object OnHiddenPassword : RegisterEvent
    data object OnHiddenConfirmPassword : RegisterEvent

    data object OnRegister : RegisterEvent
}