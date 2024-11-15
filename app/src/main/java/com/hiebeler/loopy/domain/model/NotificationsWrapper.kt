package com.hiebeler.loopy.domain.model

data class NotificationsWrapper(
    val data: List<Notification>,
    val nextCursor: String,
    val previousCursor: String
)
