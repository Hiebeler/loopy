package com.hiebeler.loopy.ui.composables.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
   private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val domainRegex: Regex =
        "^((\\*)|((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)|((\\*\\.)?([a-zA-Z0-9-]+\\.){0,5}[a-zA-Z0-9-][a-zA-Z0-9-]+\\.[a-zA-Z]{2,63}?))\$".toRegex()

    private val emailRegex: Regex = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))\$".toRegex()

    var customUrl: String by mutableStateOf("loops.video")
    var email: String by mutableStateOf("hiebeler.daniel@gmail.com")
    var password: String by mutableStateOf("")

    var loginState by mutableStateOf(LoginState())

    var isValidUrl: Boolean by mutableStateOf(false)
    var isValidEmail: Boolean by mutableStateOf(false)

    fun domainChanged() {
        isValidUrl = domainRegex.matches(customUrl)
    }

    fun emailChanged() {
        isValidEmail = emailRegex.matches(email)
    }

    fun login() {
        if (domainRegex.matches(customUrl) && emailRegex.matches(email)) {
            loginUseCase(customUrl, email, password).onEach { result ->
                if (result.success) {

                }

                loginState = result
            }.launchIn(viewModelScope)
        }
    }
}