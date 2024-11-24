package com.hiebeler.loopy.domain.model

data class Wrapper<T> (
    val data: List<T> = emptyList(),
    val previousCursor: String? = null,
    val nextCursor: String? = null
)