package com.hiebeler.loopy.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val actor: Account, val createdAt: String, val id: String, val type: String, val videoId: String?, val videoThumbnail: String?
)
