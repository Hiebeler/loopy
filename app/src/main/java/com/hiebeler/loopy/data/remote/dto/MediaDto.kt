package com.hiebeler.loopy.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.hiebeler.loopy.domain.model.Media

data class MediaDto(
    @SerializedName("height") val height: Int,
    @SerializedName("src_url") val srcUrl: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("width") val width: Int
) : DtoInterface<Media> {
    override fun toModel(): Media {
        return Media(
            height = height, srcUrl = srcUrl, thumbnail = thumbnail, width = width
        )
    }
}