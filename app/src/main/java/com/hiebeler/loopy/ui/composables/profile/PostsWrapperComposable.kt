package com.hiebeler.loopy.ui.composables.profile


import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import com.hiebeler.loopy.domain.model.FeedWrapper
import com.hiebeler.loopy.ui.composables.post.SmallPost

fun LazyGridScope.PostsWrapperComposable(
    feedWrapper: FeedWrapper?,
) {
    if (feedWrapper != null) {
        items(feedWrapper.data) {post ->
            SmallPost(post)
        }
    }
}