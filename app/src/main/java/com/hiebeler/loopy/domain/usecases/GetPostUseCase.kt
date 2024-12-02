package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostUseCase(
    private val postRepository: PostRepository
) {
    operator fun invoke(postId: String): Flow<Resource<Post>> {
        return postRepository.getPost(postId)
    }
}