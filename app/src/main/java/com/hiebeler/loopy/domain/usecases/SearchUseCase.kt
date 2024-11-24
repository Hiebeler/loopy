package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.Wrapper
import com.hiebeler.loopy.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchUseCase(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(query: String, nextCursor: String = ""): Flow<Resource<Wrapper<Account>>> {
        return searchRepository.search(query, nextCursor)
    }
}