package com.hiebeler.loopy.ui.composables.profile.other_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hiebeler.loopy.R
import com.hiebeler.loopy.ui.composables.InfiniteListHandler
import com.hiebeler.loopy.ui.composables.own_profile.OtherProfileViewModel
import com.hiebeler.loopy.ui.composables.profile.PostsWrapperComposable
import com.hiebeler.loopy.ui.composables.profile.ProfileTopSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherProfileComposable(
    navController: NavController,
    userId: String,
    viewModel: OtherProfileViewModel = hiltViewModel(key = "other-profile$userId")
) {
    val lazyListState = rememberLazyListState()
    val shareSheetState = rememberModalBottomSheetState()
    var showShareSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadData(userId, false)
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(windowInsets = WindowInsets(0, 0, 0, 0),
            title = {},
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = ""
                    )
                }
            },
            actions = {
                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert, contentDescription = ""
                    )
                }
            })
    }) { padding ->

        PullToRefreshBox(
            isRefreshing = viewModel.profileState.isRefreshing || viewModel.postsState.refreshing,
            onRefresh = { viewModel.refresh() },
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 12.dp)
                .fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                state = lazyListState
            ) {
                item {
                    if (viewModel.profileState.data != null) {
                        if (viewModel.profileState.isLoading) {
                            CircularProgressIndicator()
                        } else if (viewModel.profileState.data != null) {
                            Column {
                                ProfileTopSection(viewModel.profileState.data!!, navController)
                                Spacer(Modifier.height(12.dp))
                                Button(
                                    onClick = {
                                        if (viewModel.profileState.data!!.following) {
                                            viewModel.unfollowAccount(viewModel.profileState.data!!.id)
                                        } else {
                                            viewModel.followAccount(viewModel.profileState.data!!.id)
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    contentPadding = PaddingValues(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (viewModel.profileState.data!!.following) {
                                            MaterialTheme.colorScheme.secondaryContainer
                                        } else {
                                            MaterialTheme.colorScheme.primary
                                        },
                                        contentColor = if (viewModel.profileState.data!!.following) {
                                            MaterialTheme.colorScheme.onSecondaryContainer
                                        } else {
                                            MaterialTheme.colorScheme.onPrimary
                                        }
                                    )
                                ) {
                                    if (viewModel.followIsLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(20.dp), color = Color.White
                                        )
                                    } else {
                                        Text(
                                            text = stringResource(
                                                if (viewModel.profileState.data!!.following) {
                                                    R.string.unfollow
                                                } else {
                                                    R.string.follow
                                                }
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                PostsWrapperComposable(viewModel.postsState.feed)
            }
        }
    }

    if (viewModel.postsState.feed != null) {
        InfiniteListHandler(lazyListState = lazyListState) {
            viewModel.loadMorePosts()
        }
    }

    if (showShareSheet && viewModel.profileState.data != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showShareSheet = false
            }, sheetState = shareSheetState
        ) {
            ShareSheetComposable(viewModel.profileState.data!!)
        }
    }

}

