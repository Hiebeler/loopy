package com.hiebeler.loopy.domain.repository

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.MetaAccount
import com.hiebeler.loopy.domain.model.Notification
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.Wrapper
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getOwnUser(): Flow<Resource<Account>>
    fun getUser(accountId: String): Flow<Resource<Account>>
    fun getPostsOfUser(accountId: String, nextCursor: String): Flow<Resource<Wrapper<Post>>>
    fun getPostsOfOwnUser(accountId: String): Flow<Resource<Wrapper<Post>>>
    fun getFollowers(accountId: String): Flow<Resource<Wrapper<Account>>>
    fun getFollowing(accountId: String): Flow<Resource<Wrapper<Account>>>
    fun getNotifications(nextCursor: String): Flow<Resource<Wrapper<Notification>>>
    fun followUser(accountId: String): Flow<Resource<MetaAccount>>
    fun unfollowUser(accountId: String): Flow<Resource<MetaAccount>>
}