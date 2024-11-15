package com.hiebeler.loopy.ui.composables.inbox

import com.hiebeler.loopy.domain.model.Notification

data class InboxState(
    val isLoading: Boolean = false,
    val refreshing: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val nextCursor: String = "",
    val previousCursor: String = "",
    val error: String = ""
)
