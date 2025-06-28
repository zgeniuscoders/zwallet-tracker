package cd.zgeniuscoders.zwallet.core.utils

import androidx.navigation.NavBackStackEntry
import cd.zgeniuscoders.zwallet.core.navigation.Route

fun NavBackStackEntry?.fromRoute(): Route {
    this?.destination?.route?.substringBefore("?")?.substringBefore("/")
        ?.substringAfterLast(".")?.let {
            return when (it) {
                Route.Dashboard::class.simpleName -> Route.Dashboard
                Route.MainPage::class.simpleName -> Route.MainPage
                Route.LoginPage::class.simpleName -> Route.LoginPage
                Route.RegisterPage::class.simpleName -> Route.RegisterPage
                Route.ForgotPasswordPage::class.simpleName -> Route.ForgotPasswordPage
                Route.Expenses::class.simpleName -> Route.Expenses
                Route.Debts::class.simpleName -> Route.Debts
                Route.Income::class.simpleName -> Route.Income
                Route.Profile::class.simpleName -> Route.Profile
                else -> Route.Dashboard
            }
        }
    return Route.Dashboard
}
