package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.FollowersWrapper
import com.hiebeler.loopy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFollowingUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(accountId: String = ""): Flow<Resource<FollowersWrapper?>> = flow {
        emit(Resource.Loading())
        userRepository.getFollowing(accountId).collect { result ->
            if (result is Resource.Success) {
                val res: FollowersWrapper? = result.data
                emit(Resource.Success(res))
            } else {
                emit(Resource.Error(result.message ?: "Something went wrong"))
            }
        }
    }
}