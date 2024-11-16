package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.FeedWrapper
import com.hiebeler.loopy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPostsOfOwnUserUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(accountId: String = ""): Flow<Resource<FeedWrapper?>> = flow {
        emit(Resource.Loading())
        userRepository.getPostsOfOwnUser(accountId).collect { timeline ->
            if (timeline is Resource.Success) {
                val res: FeedWrapper? = timeline.data
                emit(Resource.Success(res))
            } else {
                emit(Resource.Error(timeline.message ?: "Something went wrong"))
            }
        }
    }
}