package com.hiebeler.loopy.ui.composables.explore

import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.Wrapper

data class SearchState(
    val isLoading: Boolean = false,
    val searchResult: Wrapper<Account>? = null,
    val error: String = ""
)
