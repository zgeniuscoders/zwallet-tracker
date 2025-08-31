package cd.zgeniuscoders.zwallet.modules.expense.presentation.ui.splash_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cd.zgeniuscoders.zwallet.core.utils.Constant
import cd.zgeniuscoders.zwallet.core.domains.services.LocalStorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private var localStorageService: LocalStorageService
) : ViewModel() {

    var state by mutableStateOf(SplashScreenState())
        private set

    init {
        onInitPage()
    }

    fun onInitPage() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                localStorageService
                    .get<Boolean>(Constant.IS_AUTHENTICATED, false)
                    .collect { res ->
                        Log.i("ZWALLET_INFO", res.toString())
                        state = state.copy(isAuthenticated = res)
                    }
            }
        }
    }

}