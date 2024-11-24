package com.hiebeler.loopy.ui.composables.profile


import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.Wrapper
import com.hiebeler.loopy.ui.composables.post.SmallPost

fun LazyGridScope.PostsWrapperComposable(
    feedWrapper: Wrapper<Post>?,
) {
    if (feedWrapper != null) {
        items(feedWrapper.data) {post ->
            SmallPost(post)
        }
    }
}