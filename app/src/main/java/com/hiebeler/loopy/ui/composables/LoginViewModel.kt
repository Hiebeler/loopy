package com.hiebeler.loopy.ui.composables

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.daniebeler.pfpixelix.domain.model.Application
import com.daniebeler.pfpixelix.domain.model.LoginData
import com.daniebeler.pfpixelix.domain.repository.CountryRepository
import com.daniebeler.pfpixelix.domain.usecase.AddNewLoginUseCase
import com.daniebeler.pfpixelix.domain.usecase.UpdateLoginDataUseCase
import com.daniebeler.pfpixelix.utils.Navigate
import com.hiebeler.loopy.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val domainRegex: Regex =
        "^((\\*)|((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)|((\\*\\.)?([a-zA-Z0-9-]+\\.){0,5}[a-zA-Z0-9-][a-zA-Z0-9-]+\\.[a-zA-Z]{2,63}?))\$".toRegex()

    var customUrl: String by mutableStateOf("")
    var isValidUrl: Boolean by mutableStateOf(false)

    var loading: Boolean by mutableStateOf(false)


    private suspend fun setBaseUrl(_baseUrl: String): String {
        var baseUrl = _baseUrl
        if (!baseUrl.startsWith("https://")) {
            baseUrl = "https://$baseUrl"
        }
        newLoginDataUseCase(LoginData(baseUrl = baseUrl, loginOngoing = true))
        return baseUrl
    }

    fun domainChanged() {
        isValidUrl = domainRegex.matches(customUrl)
    }

    suspend fun login(email: String, password: String) {
        loading = true
        if (domainRegex.matches(email)) {


        }
        loading = false
    }
}