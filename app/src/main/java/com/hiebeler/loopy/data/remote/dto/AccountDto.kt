package com.hiebeler.loopy.data.remote.dto

import com.hiebeler.loopy.domain.model.Account

data class AccountDto(
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
) : DtoInterface<Account> {
    override fun toModel(): Account {
        return Account(
            id = id,
            name = name,
            username = username,
            bio = bio,
            avatar = avatar,
            created_at = created_at,
            follower_count = follower_count,
            following_count = following_count,
            is_blocking = is_blocking,
            is_owner = is_owner,
            post_count = post_count,
            url = url
        )
    }
}