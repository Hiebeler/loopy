package com.hiebeler.loopy.ui.composables.post

import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.Wrapper

data class PostsState(
    val isLoading: Boolean = false,
    val refreshing: Boolean = false,
    val endReached: Boolean = false,
    val feed: Wrapper<Post>? = null,
    val error: String = ""
)