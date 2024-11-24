package com.hiebeler.loopy.domain.repository

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.Wrapper
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(query: String, nextCursor: String = ""): Flow<Resource<Wrapper<Account>>>
}