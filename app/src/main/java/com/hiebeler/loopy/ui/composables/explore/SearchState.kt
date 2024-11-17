package com.hiebeler.loopy.ui.composables.explore

import com.hiebeler.loopy.domain.model.SearchWrapper

data class SearchState(
    val isLoading: Boolean = false,
    val searchResult: SearchWrapper? = null,
    val error: String = ""
)
