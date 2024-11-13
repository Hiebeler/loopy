package com.hiebeler.loopy.ui.composables.home

import android.app.ActionBar.LayoutParams
import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.ui.composables.post.LargePost
import com.hiebeler.loopy.utils.Navigate
import sv.lib.squircleshape.SquircleShape

@Composable
fun HomeComposable(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(key = "home-viewmodel-key")
) {
    val pagerState = rememberPagerState(pageCount = {
        Int.MAX_VALUE
    })
    Scaffold { padding ->
        Box(modifier = Modifier.padding(padding)) {
            VerticalPager(state = pagerState, beyondViewportPageCount = 1) { pageIndex: Int ->
                if (viewModel.feedState.isLoading || viewModel.feedState.feed == null) {
                    CircularProgressIndicator()
                } else {
                    if (pageIndex >= viewModel.feedState.feed!!.data.size - 2 && viewModel.feedState.feed!!.data.isNotEmpty()) {
                        LaunchedEffect(pageIndex) {
                            viewModel.loadMorePosts(viewModel.feedState.feed!!.meta.nextCursor)
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

            }/*LazyColumn {
                items(viewModel.feedState.feed) { item ->
                    Post(item)
                    Spacer(Modifier.height(18.dp))
                }

                if(viewModel.feedState.isLoading) {
                    item {
                        CircularProgressIndicator()
                    }
                }
            }*/

            if (viewModel.feedState.error.isNotEmpty()) {
                Box {
                    Text(viewModel.feedState.error)
                }
            }
        }
    }
}

