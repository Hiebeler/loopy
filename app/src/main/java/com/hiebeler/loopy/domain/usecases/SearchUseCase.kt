package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.MetaAccount
import com.hiebeler.loopy.domain.model.SearchWrapper
import com.hiebeler.loopy.domain.repository.SearchRepository
import com.hiebeler.loopy.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class SearchUseCase(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(query: String): Flow<Resource<SearchWrapper>> {
        return searchRepository.search(query)
    }
}