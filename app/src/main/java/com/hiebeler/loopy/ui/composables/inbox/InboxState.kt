package com.hiebeler.loopy.ui.composables.inbox

import com.hiebeler.loopy.domain.model.NotificationsWrapper

data class InboxState(
    val isLoading: Boolean = false,
    val refreshing: Boolean = false,
    val inbox: NotificationsWrapper? = null,
    val error: String = ""
)
