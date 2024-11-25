package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.Wrapper
import com.hiebeler.loopy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetPostsOfUserUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(accountId: String = "", nextCursor: String = ""): Flow<Resource<Wrapper<Post>>> {
        return userRepository.getPostsOfUser(accountId, nextCursor)
    }
}