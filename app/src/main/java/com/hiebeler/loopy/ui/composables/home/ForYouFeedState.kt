package com.hiebeler.loopy.ui.composables.home

import com.hiebeler.loopy.domain.model.Post

data class ForYouFeedState(
    val isLoading: Boolean = false,
    val refreshing: Boolean = false,
    val feed: List<Post> = emptyList(),
    val error: String = ""
)
