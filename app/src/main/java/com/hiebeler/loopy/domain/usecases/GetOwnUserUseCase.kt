package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetOwnUserUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Resource<Account>> = flow {
        emit(Resource.Loading())
        userRepository.getOwnUser().collect { res ->
            emit(res)
        }
    }
}