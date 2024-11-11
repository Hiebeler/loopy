package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.domain.repository.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.logout()
    }
}