package com.hiebeler.loopy.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.hiebeler.loopy.domain.model.Post

data class PostDto(
    @SerializedName("account") val account: AccountDto,
    @SerializedName("caption") val caption: String?,
    @SerializedName("comments") val comments: Int,
    @SerializedName("has_liked") val hasLiked: Boolean,
    @SerializedName("id") val id: String,
    @SerializedName("is_owner") val isOwner: Boolean,
    @SerializedName("is_sensitive") val isSensitive: Boolean,
    @SerializedName("likes") val likes: Int,
    @SerializedName("media") val media: MediaDto,
    @SerializedName("shares") val shares: Int,
    @SerializedName("url") val url: String
) : DtoInterface<Post> {
    override fun toModel(): Post {
        return Post(
            account = account.toModel(),
            caption = caption ?: "",
            comments = comments,
            hasLiked = hasLiked,
            id = id,
            isOwner = isOwner,
            isSensitive = isSensitive,
            likes = likes,
            media = media.toModel(),
            shares = shares,
            url = url
        )
    }
}