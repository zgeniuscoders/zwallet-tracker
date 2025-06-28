package cd.zgeniuscoders.floapp.ui.screens.login

data class LoginState(
    var isLoading: Boolean = false,
    var errorMessages: String = "",
    var isLogged: Boolean = false,
    var isPasswordVisible: Boolean = false,
    var email: String = "",
    var password: String = ""
)
