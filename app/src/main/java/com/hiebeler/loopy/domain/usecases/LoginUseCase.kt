package com.hiebeler.loopy.domain.usecases

import androidx.compose.foundation.isSystemInDarkTheme
import com.daniebeler.pfpixelix.di.HostSelectionInterceptorInterface
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.LoginData
import com.hiebeler.loopy.domain.model.LoginModel
import com.hiebeler.loopy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase(
    private val authRepository: AuthRepository,
    private val hostSelectionInterceptorInterface: HostSelectionInterceptorInterface
) {
    suspend operator fun invoke(baseUrl: String, email: String, password: String): Flow<Resource<LoginModel>> = flow {
        emit(Resource.Loading())
        if (baseUrl.isNotBlank()) {
            hostSelectionInterceptorInterface.setHost(baseUrl.replace("https://", ""))        }

        authRepository.login(email, password).collect { loginModel ->
            if (loginModel is Resource.Success) {
                //Todo: add login data to authData than emit success
            } else {
                emit(loginModel)
            }
        }

    }
}