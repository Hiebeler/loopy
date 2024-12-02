package com.hiebeler.loopy.domain.repository

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun likePost(postId: String): Flow<Resource<Post>>
    fun unlikePost(postId: String): Flow<Resource<Post>>
    fun getPost(postId: String): Flow<Resource<Post>>
//    fun reblogPost(postId: String): Flow<Resource<Post>>
//    fun unreblogPost(postId: String): Flow<Resource<Post>>
//    fun bookmarkPost(postId: String): Flow<Resource<Post>>
//    fun unBookmarkPost(postId: String): Flow<Resource<Post>>
//    fun getLikedPosts(maxId: String = ""): Flow<Resource<LikedPostsWithNext>>
//    fun getBookmarkedPosts(): Flow<Resource<List<Post>>>
//    fun getTrendingPosts(range: String): Flow<Resource<List<Post>>>
}