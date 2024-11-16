package com.hiebeler.loopy.ui.composables.post

import com.hiebeler.loopy.domain.model.FeedWrapper
import com.hiebeler.loopy.domain.model.Post

data class PostsState(
    val isLoading: Boolean = false,
    val refreshing: Boolean = false,
    val endReached: Boolean = false,
    val feed: FeedWrapper? = null,
    val error: String = ""
)