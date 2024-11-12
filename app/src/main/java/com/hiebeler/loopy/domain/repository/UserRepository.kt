package com.hiebeler.loopy.domain.repository

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.FeedWrapper
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getOwnUser(): Flow<Resource<Account>>
    fun getUser(accountId: String): Flow<Resource<Account>>
    fun getPostsOfUser(accountId: String): Flow<Resource<FeedWrapper>>
}