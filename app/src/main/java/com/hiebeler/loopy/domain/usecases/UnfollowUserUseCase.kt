package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.MetaAccount
import com.hiebeler.loopy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UnfollowUserUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(accountId: String): Flow<Resource<MetaAccount>> {
        return userRepository.unfollowUser(accountId)
    }
}