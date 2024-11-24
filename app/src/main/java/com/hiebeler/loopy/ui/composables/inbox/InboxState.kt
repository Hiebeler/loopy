package com.hiebeler.loopy.ui.composables.inbox

import com.hiebeler.loopy.domain.model.Notification
import com.hiebeler.loopy.domain.model.Wrapper

data class InboxState(
    val isLoading: Boolean = false,
    val refreshing: Boolean = false,
    val wrapper: Wrapper<Notification> = Wrapper(),
    val error: String = ""
)
