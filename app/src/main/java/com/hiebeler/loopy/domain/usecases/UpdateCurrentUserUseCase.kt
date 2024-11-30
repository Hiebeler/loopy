package com.daniebeler.pfpixelix.domain.usecase

import com.daniebeler.pfpixelix.di.HostSelectionInterceptorInterface
import com.hiebeler.loopy.domain.model.LoginData
import com.hiebeler.loopy.domain.repository.AuthRepository

class UpdateCurrentUserUseCase(private val authRepository: AuthRepository, private val hostSelectionInterceptorInterface: HostSelectionInterceptorInterface) {
    suspend operator fun invoke(newLoginData: LoginData) {
        hostSelectionInterceptorInterface.setHost(newLoginData.baseUrl)
        hostSelectionInterceptorInterface.setToken(newLoginData.accessToken)
        authRepository.updateCurrentUser(newLoginData.accountId)
    }
}