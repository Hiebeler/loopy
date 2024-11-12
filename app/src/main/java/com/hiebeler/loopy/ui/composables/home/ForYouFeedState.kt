package com.hiebeler.loopy.ui.composables.home

import com.hiebeler.loopy.domain.model.FeedWrapper

data class ForYouFeedState(
    val isLoading: Boolean = false,
    val refreshing: Boolean = false,
    val feed: FeedWrapper? = null,
    val error: String = ""
)
