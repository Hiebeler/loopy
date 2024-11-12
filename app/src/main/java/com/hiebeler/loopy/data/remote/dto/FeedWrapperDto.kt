package com.hiebeler.loopy.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.hiebeler.loopy.domain.model.FeedWrapper

data class FeedWrapperDto(
    @SerializedName("data") val data: List<PostDto>,
    @SerializedName("links") val links: LinksDto,
    @SerializedName("meta") val meta: MetaDto
) : DtoInterface<FeedWrapper> {
    override fun toModel(): FeedWrapper {
        return FeedWrapper(
            data = data.map { it.toModel() },
            links = links.toModel(),
            meta = meta.toModel()
        )
    }
}