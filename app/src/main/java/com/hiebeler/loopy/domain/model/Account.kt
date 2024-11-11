package com.hiebeler.loopy.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val avatar: String,
    val bio: String,
    val created_at: String,
    val follower_count: Int,
    val following_count: Int,
    val id: String,
    val is_blocking: Any,
    val is_owner: Boolean,
    val name: String,
    val post_count: Int,
    val url: String,
    val username: String
)
