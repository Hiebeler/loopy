package com.hiebeler.loopy.domain.repository

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.FeedWrapper
import com.hiebeler.loopy.domain.model.SearchWrapper
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(query: String): Flow<Resource<SearchWrapper>>
}