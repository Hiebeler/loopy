package com.hiebeler.loopy.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.hiebeler.loopy.domain.model.FeedWrapper

data class FeedWrapperDto(
    @SerializedName("data") val data: List<PostDto>
) : DtoInterface<FeedWrapper> {
    override fun toModel(): FeedWrapper {
        return FeedWrapper(
            data = data.map { it.toModel() }
        )
    }
}