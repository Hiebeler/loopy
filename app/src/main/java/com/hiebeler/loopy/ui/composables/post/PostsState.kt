package com.hiebeler.loopy.ui.composables.post

import com.hiebeler.loopy.domain.model.Post

data class PostsState(
    val isLoading: Boolean = false,
    val refreshing: Boolean = false,
    val endReached: Boolean = false,
    val posts: List<Post> = emptyList(),
    val error: String = ""
)