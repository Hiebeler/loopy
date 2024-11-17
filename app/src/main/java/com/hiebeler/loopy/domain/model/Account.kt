package com.hiebeler.loopy.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val avatar: String,
    val bio: String,
    val createdAt: String,
    val followerCount: Int,
    val followingCount: Int,
    val id: String,
    val isBlocking: Boolean,
    val isOwner: Boolean,
    val name: String,
    val postCount: Int,
    val url: String,
    val username: String,
    val followedBy: Boolean,
    var following: Boolean
)
