package com.hiebeler.loopy.ui.composables.profile.own_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.SwitchAccount
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.daniebeler.pfpixelix.ui.composables.profile.own_profile.AccountSwitchBottomSheet
import com.hiebeler.loopy.ui.composables.InfiniteListHandler
import com.hiebeler.loopy.ui.composables.profile.PostsWrapperComposable
import com.hiebeler.loopy.ui.composables.profile.ProfileTopSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileComposable(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(key = "own-profile-key")
) {
    val lazyListState = rememberLazyListState()
    val shareSheetState = rememberModalBottomSheetState()
    var showShareSheet by remember { mutableStateOf(false) }
    var showMultipleAccountsBottomSheet by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(windowInsets = WindowInsets(0, 0, 0, 0),
            title = {},
            navigationIcon = {
                IconButton(onClick = {
                    showMultipleAccountsBottomSheet = true
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
            isRefreshing = viewModel.ownProfileState.isRefreshing || viewModel.postsState.refreshing,
            onRefresh = { viewModel.refresh() },
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp), state = lazyListState
            ) {
                item {
                    if (viewModel.ownProfileState.data != null) {
                        if (viewModel.ownProfileState.isLoading && !viewModel.ownProfileState.isRefreshing) {
                            CircularProgressIndicator()
                        } else if (viewModel.ownProfileState.data != null) {
                            ProfileTopSection(viewModel.ownProfileState.data!!, navController)
                        }
                    }
                }

                PostsWrapperComposable(viewModel.postsState.feed, navController)
            }
        }
    }

    InfiniteListHandler(lazyListState = lazyListState) {
        viewModel.loadMorePosts()
    }

    if (showShareSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showShareSheet = false
            }, sheetState = shareSheetState
        ) {
            PreferencesComposable(viewModel.ownProfileState.data, navController)
        }
    } else if (showMultipleAccountsBottomSheet) {
        ModalBottomSheet(onDismissRequest = { showMultipleAccountsBottomSheet = false }) {
            AccountSwitchBottomSheet(closeBottomSheet = {
                showMultipleAccountsBottomSheet = false
            }, ownProfileViewModel = viewModel)
        }
    }
}