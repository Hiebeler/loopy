package com.hiebeler.loopy.data.repository

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.data.remote.LoopsApi
import com.hiebeler.loopy.data.remote.dto.SearchWrapperDto
import com.hiebeler.loopy.domain.model.SearchWrapper
import com.hiebeler.loopy.domain.repository.SearchRepository
import com.hiebeler.loopy.utils.NetworkCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val loopsApi: LoopsApi
) : SearchRepository {
    override fun search(query: String, nextCursor: String): Flow<Resource<SearchWrapper>> {
        return NetworkCall<SearchWrapper, SearchWrapperDto>().makeCall(
            loopsApi.search(
                query, nextCursor
            )
        )
    }
}