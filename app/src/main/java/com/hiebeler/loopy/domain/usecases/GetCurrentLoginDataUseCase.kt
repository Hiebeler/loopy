package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.domain.model.LoginData
import com.hiebeler.loopy.domain.repository.AuthRepository

class GetCurrentLoginDataUseCase (private val repository: AuthRepository) {
    suspend operator fun invoke(): LoginData? {
        return repository.getCurrentLoginData()
    }
}