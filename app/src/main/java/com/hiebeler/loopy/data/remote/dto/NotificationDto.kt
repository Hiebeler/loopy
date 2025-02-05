package com.hiebeler.loopy.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.hiebeler.loopy.domain.model.Notification

data class NotificationDto(
    @SerializedName("actor") val actor: AccountDto,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("video_id") val videoId: String?,
    @SerializedName("video_thumbnail") val videoThumbnail: String?
) : DtoInterface<Notification> {
    override fun toModel(): Notification {
        return Notification(
            actor = actor.toModel(), createdAt = createdAt, id = id, type = type, videoId = videoId, videoThumbnail = videoThumbnail
        )
    }
}