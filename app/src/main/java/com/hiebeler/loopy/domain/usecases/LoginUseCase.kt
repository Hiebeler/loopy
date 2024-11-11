package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.login()
    }
}