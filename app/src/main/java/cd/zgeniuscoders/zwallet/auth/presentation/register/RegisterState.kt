package cd.zgeniuscoders.zwallet.auth.presentation.register

data class RegisterState(
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var username: String = "",
    var errorMessage: String = "",
    var hiddenPassword: Boolean = true,
    var hiddenConfirmPassword: Boolean = true,
    var isRegistered: Boolean = false,
    var isLoading: Boolean = false
)
