package com.hiebeler.loopy.data.repository

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.data.remote.LoopsApi
import com.hiebeler.loopy.data.remote.dto.SearchWrapperDto
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.Wrapper
import com.hiebeler.loopy.domain.repository.SearchRepository
import com.hiebeler.loopy.utils.NetworkCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val loopsApi: LoopsApi
) : SearchRepository {
    override fun search(query: String, nextCursor: String): Flow<Resource<Wrapper<Account>>> {
        return NetworkCall<Wrapper<Account>, SearchWrapperDto>().makeCall(
            loopsApi.search(
                query, nextCursor
            )
        )
    }
}