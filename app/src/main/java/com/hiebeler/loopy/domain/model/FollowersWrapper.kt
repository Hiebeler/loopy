package com.hiebeler.loopy.domain.model

data class FollowersWrapper(
    val data: List<Account>,
    val links: Links,
    val meta: Meta
)
