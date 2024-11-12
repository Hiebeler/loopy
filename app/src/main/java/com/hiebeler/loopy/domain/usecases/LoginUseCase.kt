package com.hiebeler.loopy.domain.usecases

import com.daniebeler.pfpixelix.di.HostSelectionInterceptorInterface
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.LoginData
import com.hiebeler.loopy.domain.model.LoginModel
import com.hiebeler.loopy.domain.repository.AuthRepository
import com.hiebeler.loopy.domain.repository.UserRepository
import com.hiebeler.loopy.ui.composables.login.LoginState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val hostSelectionInterceptorInterface: HostSelectionInterceptorInterface
) {
    operator fun invoke(
        baseUrl: String, email: String, password: String
    ): Flow<LoginState> = flow {
        emit(LoginState(isLoading = true))
        if (baseUrl.isNotBlank()) {
            hostSelectionInterceptorInterface.setHost(baseUrl.replace("https://", ""))
        }

        authRepository.login(email, password).collect { loginModel ->
            if (loginModel is Resource.Success) {
                if (loginModel.data?.authToken?.isNotEmpty() == true) {
                    hostSelectionInterceptorInterface.setToken(loginModel.data.authToken)

                    userRepository.getOwnUser().collect { user ->
                        if (user is Resource.Success) {

                            val userData = user.data!!

                            val newLoginData = LoginData(
                                accountId = userData.id,
                                username = userData.username,
                                displayName = userData.name,
                                avatar = userData.avatar,
                                accessToken = loginModel.data.authToken,
                                baseUrl = userData.url
                            )

                            authRepository.finishLogin(newLoginData, userData.id)
                            emit(LoginState(success = true))
                        }
                    }
                }
            } else if (loginModel is Resource.Error) {
                emit(LoginState(error = loginModel.message ?: "Something went wrong"))
            }
        }

    }
}