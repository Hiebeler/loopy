package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.repository.TimelineRepository
import com.hiebeler.loopy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPostsOfUserUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(accountId: String = ""): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading())
        userRepository.getPostsOfUser(accountId).collect { timeline ->
            if (timeline is Resource.Success) {
                val res: List<Post> = timeline.data?.data ?: emptyList()
                emit(Resource.Success(res))
            } else {
                emit(Resource.Error(timeline.message ?: "Something went wrong"))
            }
        }
    }
}