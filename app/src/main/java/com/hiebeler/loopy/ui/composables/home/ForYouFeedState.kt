package com.hiebeler.loopy.ui.composables.home

import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.Wrapper

data class ForYouFeedState(
    val isLoading: Boolean = false,
    val refreshing: Boolean = false,
    val feed: Wrapper<Post>? = null,
    val error: String = ""
)
