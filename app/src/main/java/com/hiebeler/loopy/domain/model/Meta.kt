package com.hiebeler.loopy.domain.model

data class Meta(
    val nextCursor: String?,
    val path: String,
    val perPage: Int,
    val prevCursor: String?
)