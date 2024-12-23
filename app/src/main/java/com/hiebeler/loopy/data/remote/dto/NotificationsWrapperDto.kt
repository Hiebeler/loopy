package com.hiebeler.loopy.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.hiebeler.loopy.domain.model.Notification
import com.hiebeler.loopy.domain.model.Wrapper

data class NotificationsWrapperDto(
    @SerializedName("data") val data: List<NotificationDto>,
    @SerializedName("links") val links: LinksDto,
    @SerializedName("meta") val meta: MetaDto
) : DtoInterface<Wrapper<Notification>> {
    override fun toModel(): Wrapper<Notification> {
        return Wrapper(
            data = data.map { it.toModel() },
            nextCursor = meta.toModel().nextCursor,
            previousCursor = meta.toModel().prevCursor
        )
    }
}