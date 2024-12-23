package com.hiebeler.loopy.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.ViewEnum
import com.hiebeler.loopy.domain.model.Wrapper
import com.hiebeler.loopy.ui.composables.post.LargePost
import com.hiebeler.loopy.ui.composables.post.SmallPost

@Composable
fun InfinitePosts(
    view: ViewEnum,
    feedWrapper: Wrapper<Post>?,
    isLoading: Boolean,
    error: String,
    activePostChanged: (index: Int) -> Unit,
    navController: NavController
) {
    when (view) {
        ViewEnum.Loading -> {
            CircularProgressIndicator()
        }

        ViewEnum.Grid -> {
            InfinitePostsGrid(feedWrapper, isLoading, error, activePostChanged, navController)
        }

        ViewEnum.Timeline -> {
            InfinitePostsTimeline(feedWrapper, isLoading, error, activePostChanged, navController)
        }
    }
}

@Composable
private fun InfinitePostsGrid(
    feedWrapper: Wrapper<Post>?,
    isLoading: Boolean,
    error: String,
    loadMorePosts: (cursor: Int) -> Unit,
    navController: NavController
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(12.dp),
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (feedWrapper != null) {
            items(feedWrapper.data) { post ->
                SmallPost(post, navController, Modifier)
            }
        }
    }
}

@Composable
private fun InfinitePostsTimeline(
    feedWrapper: Wrapper<Post>?,
    isLoading: Boolean,
    error: String,
    activePostChanged: (index: Int) -> Unit,
    navController: NavController
) {
    val pagerState = rememberPagerState(pageCount = {
        Int.MAX_VALUE
    })

    Box(modifier = Modifier.fillMaxSize()) {
        VerticalPager(state = pagerState, beyondViewportPageCount = 1) { pageIndex: Int ->

            LaunchedEffect(pageIndex) {
                activePostChanged(pageIndex)
            }

            if (feedWrapper == null || (feedWrapper.data.isEmpty() && isLoading)) {
                CircularProgressIndicator()
            } else {
                var item: Post? = null
                if (pageIndex < feedWrapper.data.size) {
                    item = feedWrapper.data[pageIndex]
                } else {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                }

                item?.let {
                    val active = pageIndex == pagerState.currentPage
                    LargePost(item, active, navController)
                }
            }
        }
    }
}