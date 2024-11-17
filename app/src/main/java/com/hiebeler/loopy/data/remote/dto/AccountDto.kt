package com.hiebeler.loopy.data.remote.dto

import com.hiebeler.loopy.domain.model.Account

data class AccountDto(
    val avatar: String?,
    val bio: String?,
    val created_at: String?,
    val follower_count: Int?,
    val following_count: Int?,
    val id: String,
    val is_blocking: Boolean?,
    val is_owner: Boolean?,
    val name: String?,
    val post_count: Int?,
    val url: String?,
    val username: String
) : DtoInterface<Account> {
    override fun toModel(): Account {
        return Account(
            id = id,
            name = name ?: "",
            username = username,
            bio = bio ?: "",
            avatar = avatar ?: "",
            createdAt = created_at ?: "",
            followerCount = follower_count ?: 0,
            followingCount = following_count ?: 0,
            isBlocking = is_blocking ?: false,
            isOwner = is_owner ?: false,
            postCount = post_count ?: 0,
            url = url ?: "",
            following = false,
            followedBy = false
        )
    }
}