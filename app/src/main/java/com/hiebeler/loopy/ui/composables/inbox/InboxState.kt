package com.hiebeler.loopy.ui.composables.inbox

import com.hiebeler.loopy.domain.model.FeedWrapper

data class InboxState(
    val isLoading: Boolean = false,
    val refreshing: Boolean = false,
    val inbox: FeedWrapper? = null,
    val error: String = ""
)
