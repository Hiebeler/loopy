package com.hiebeler.loopy.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val account: Account,
    val caption: String,
    val comments: Int,
    val hasLiked: Boolean,
    val id: String,
    val isOwner: Boolean,
    val isSensitive: Boolean,
    val likes: Int,
    val media: Media,
    val shares: Int,
    val url: String
)
