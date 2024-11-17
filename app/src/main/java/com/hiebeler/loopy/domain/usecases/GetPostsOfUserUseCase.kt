package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.FeedWrapper
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.repository.TimelineRepository
import com.hiebeler.loopy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPostsOfUserUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(accountId: String = ""): Flow<Resource<FeedWrapper>> {
        return userRepository.getPostsOfUser(accountId)
    }
}