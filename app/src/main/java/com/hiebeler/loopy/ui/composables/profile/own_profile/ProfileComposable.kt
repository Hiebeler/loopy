package com.hiebeler.loopy.ui.composables.profile.own_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.SwitchAccount
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hiebeler.loopy.ui.composables.profile.PostsWrapperComposable
import com.hiebeler.loopy.ui.composables.profile.ProfileTopSection
import com.hiebeler.loopy.ui.composables.profile.other_profile.ShareSheetComposable
import com.hiebeler.loopy.ui.composables.profile.own_profile.ProfileViewModel
import sv.lib.squircleshape.SquircleShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileComposable(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(key = "own-profile-key")
) {
    val lazyGridState = rememberLazyGridState()
    val shareSheetState = rememberModalBottomSheetState()
    var showShareSheet by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(windowInsets = WindowInsets(0, 0, 0, 0),
            title = {},
            navigationIcon = {
                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Outlined.SwitchAccount,
                        contentDescription = "account switch dropdown",
                        Modifier.size(24.dp)
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    showShareSheet = true
                }) {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert, contentDescription = ""
                    )
                }
            })
    }) { padding ->
        PullToRefreshBox(
            isRefreshing = viewModel.ownProfileState.refreshing || viewModel.postsState.refreshing,
            onRefresh = { viewModel.refresh() },
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                // modifier = Modifier.pullRefresh(pullRefreshState),
                state = lazyGridState
            ) {
                item(span = { GridItemSpan(3) }) {
                    if (viewModel.ownProfileState.user != null) {
                        if (viewModel.ownProfileState.isLoading) {
                            CircularProgressIndicator()
                        } else if (viewModel.ownProfileState.user != null) {
                            ProfileTopSection(viewModel.ownProfileState.user!!, navController)
                        }
                    }
                }
                PostsWrapperComposable(viewModel.postsState.feed)
            }
        }
    }

    if (showShareSheet && viewModel.ownProfileState.user != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showShareSheet = false
            }, sheetState = shareSheetState
        ) {
            PreferencesComposable(viewModel.ownProfileState.user!!, navController)
        }
    }
}