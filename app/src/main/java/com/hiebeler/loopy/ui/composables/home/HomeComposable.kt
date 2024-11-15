package com.hiebeler.loopy.ui.composables.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.ui.composables.post.LargePost

@Composable
fun HomeComposable(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(key = "home-viewmodel-key")
) {
    val pagerState = rememberPagerState(pageCount = {
        Int.MAX_VALUE
    })
    Scaffold { padding ->
        Box(modifier = Modifier.padding()) {
            VerticalPager(state = pagerState, beyondViewportPageCount = 1) { pageIndex: Int ->
                if (viewModel.feedState.isLoading || viewModel.feedState.feed == null) {
                    CircularProgressIndicator()
                } else {
                    if (pageIndex >= viewModel.feedState.feed!!.data.size - 2 && viewModel.feedState.feed!!.data.isNotEmpty() && viewModel.feedState.feed?.meta?.nextCursor != null) {
                        LaunchedEffect(pageIndex) {
                            viewModel.feedState.feed!!.meta.nextCursor?.let {
                                viewModel.loadMorePosts(
                                    it
                                )
                            }
                        }
                    }
                    var item: Post? = null
                    if (pageIndex < viewModel.feedState.feed!!.data.size) {
                        item = viewModel.feedState.feed!!.data[pageIndex]
                    } else {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator()
                        }
                    }
                    item?.let {
                        LargePost(item, navController)
                    }
                }
            }

            if (viewModel.feedState.error.isNotEmpty()) {
                Box {
                    Text(viewModel.feedState.error)
                }
            }
        }
    }
}

