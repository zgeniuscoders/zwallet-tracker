package cd.zgeniuscoders.zwallet.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    data object MainPage: Route

    @Serializable
    data object Dashboard: Route

    @Serializable
    data object Expenses: Route

    @Serializable
    data object Income: Route

    @Serializable
    data object Debts: Route

    @Serializable
    data object Profile: Route

    @Serializable
    data object LoginPage: Route

    @Serializable
    data object RegisterPage: Route

    @Serializable
    data object ForgotPasswordPage: Route
}