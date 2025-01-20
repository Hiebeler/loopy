package com.hiebeler.loopy.ui.composables.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.ViewEnum
import com.hiebeler.loopy.ui.composables.InfinitePosts
import com.hiebeler.loopy.ui.composables.post.LargePost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeComposable(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(key = "home-viewmodel-key")
) {
    Scaffold { padding ->
        PullToRefreshBox(
            isRefreshing = viewModel.feedState.isRefreshing,
            onRefresh = { viewModel.refresh() },
            modifier = Modifier.padding()
        ) {
            InfinitePosts(
                ViewEnum.Timeline,
                viewModel.feedState.data,
                viewModel.feedState.isLoading,
                viewModel.feedState.error,
                { index -> viewModel.activePostChanged(index) },
                navController
            )
        }
    }
}

