package com.hiebeler.loopy.data.repository


import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.data.remote.LoopsApi
import com.hiebeler.loopy.data.remote.dto.PostDto
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.repository.PostRepository
import com.hiebeler.loopy.utils.NetworkCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val pixelfedApi: LoopsApi
) : PostRepository {

    override fun likePost(postId: String): Flow<Resource<Post>> {
        return NetworkCall<Post, PostDto>().makeCall(pixelfedApi.likePost(postId))
    }

    override fun unlikePost(postId: String): Flow<Resource<Post>> {
        return NetworkCall<Post, PostDto>().makeCall(pixelfedApi.unlikePost(postId))
    }
}