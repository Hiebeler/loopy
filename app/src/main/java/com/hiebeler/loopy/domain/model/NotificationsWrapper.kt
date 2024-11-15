package com.hiebeler.loopy.domain.model

data class NotificationsWrapper(
    val data: List<Notification>,
    val links: Links,
    val meta: Meta
)
