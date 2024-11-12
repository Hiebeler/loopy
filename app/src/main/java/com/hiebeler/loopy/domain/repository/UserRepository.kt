package com.hiebeler.loopy.domain.repository

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getOwnUser(): Flow<Resource<Account>>
}