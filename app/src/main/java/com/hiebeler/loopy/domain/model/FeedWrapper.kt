package com.hiebeler.loopy.domain.model

data class FeedWrapper(
    val data: List<Post>,
    val links: Links,
    val meta: Meta
)
