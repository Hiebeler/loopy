package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.domain.model.AuthData
import com.hiebeler.loopy.domain.repository.AuthRepository

class GetAuthDataUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): AuthData {
        return repository.getAuthData()
    }
}