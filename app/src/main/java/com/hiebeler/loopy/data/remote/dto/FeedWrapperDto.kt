package com.hiebeler.loopy.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.Wrapper

data class FeedWrapperDto(
    @SerializedName("data") val data: List<PostDto>,
    @SerializedName("links") val links: LinksDto,
    @SerializedName("meta") val meta: MetaDto
) : DtoInterface<Wrapper<Post>> {
    override fun toModel(): Wrapper<Post> {
        return Wrapper(
            data = data.map { it.toModel() },
            previousCursor = meta.toModel().prevCursor,
            nextCursor = meta.toModel().nextCursor
        )
    }
}