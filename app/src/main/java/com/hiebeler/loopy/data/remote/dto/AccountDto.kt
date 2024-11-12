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
            created_at = created_at ?: "",
            follower_count = follower_count ?: 0,
            following_count = following_count ?: 0,
            is_blocking = is_blocking ?: false,
            is_owner = is_owner ?: false,
            post_count = post_count ?: 0,
            url = url ?: ""
        )
    }
}