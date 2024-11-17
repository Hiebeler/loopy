package com.hiebeler.loopy.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.hiebeler.loopy.domain.model.Account

data class AccountWrapperDto(
    @SerializedName("data") val data: AccountDto,
    @SerializedName("meta") val meta: MetaAccountDto
): DtoInterface<Account> {
    override fun toModel(): Account {
        return Account(
            id = data.id,
            name = data.name ?: "",
            username = data.username,
            bio = data.bio ?: "",
            avatar = data.avatar ?: "",
            createdAt = data.created_at ?: "",
            followerCount = data.follower_count ?: 0,
            followingCount = data.following_count ?: 0,
            isBlocking = data.is_blocking ?: false,
            isOwner = data.is_owner ?: false,
            postCount = data.post_count ?: 0,
            url = data.url ?: "",
            followedBy = meta.followed_by,
            following = meta.following
        )
    }
}