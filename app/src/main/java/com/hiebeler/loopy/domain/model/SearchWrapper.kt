package com.hiebeler.loopy.domain.model

data class SearchWrapper(
    val users: List<Account>, val nextCursor: String, val previousCursor: String
)