package com.hiebeler.loopy.ui.composables

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.usecases.LoginUseCase
import com.hiebeler.loopy.ui.composables.login.LoginState
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

    var customUrl: String by mutableStateOf("")
    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")

    var loginState by mutableStateOf(LoginState())

    var isValidUrl: Boolean by mutableStateOf(false)
    var isValidEmail: Boolean by mutableStateOf(false)

    var loading: Boolean by mutableStateOf(false)


    private suspend fun createBaseUrl(_baseUrl: String): String {
        var baseUrl = _baseUrl
        if (!baseUrl.startsWith("https://")) {
            baseUrl = "https://$baseUrl"
        }
        return baseUrl
    }

    fun domainChanged() {
        isValidUrl = domainRegex.matches(customUrl)
    }

    fun emailChanged() {
        isValidEmail = emailRegex.matches(email)
    }

    suspend fun login() {
        loading = true
        if (domainRegex.matches(customUrl) && emailRegex.matches(email)) {
            loginUseCase(customUrl, email, password).onEach { result ->
                loginState = when (result) {
                    is Resource.Success -> {
                        LoginState(success = true)
                    }

                    is Resource.Error -> {
                        LoginState(error = result.message ?: "An error occurred")
                    }

                    is Resource.Loading -> {
                        LoginState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
        loading = false
    }
}