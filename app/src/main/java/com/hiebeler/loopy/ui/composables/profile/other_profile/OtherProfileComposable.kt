package com.hiebeler.loopy.ui.composables.profile.other_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hiebeler.loopy.R
import com.hiebeler.loopy.domain.model.ViewEnum
import com.hiebeler.loopy.ui.composables.InfinitePosts
import com.hiebeler.loopy.ui.composables.own_profile.OtherProfileViewModel
import com.hiebeler.loopy.ui.composables.post.SmallPost
import com.hiebeler.loopy.ui.composables.profile.PostsWrapperComposable
import com.hiebeler.loopy.ui.composables.profile.ProfileTopSection
import com.hiebeler.loopy.ui.composables.profile.followers.FollowersComposable
import sv.lib.squircleshape.SquircleShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherProfileComposable(
    navController: NavController,
    userId: String,
    viewModel: OtherProfileViewModel = hiltViewModel(key = "other-profile$userId")
) {
    val lazyGridState = rememberLazyGridState()
    val shareSheetState = rememberModalBottomSheetState()
    var showShareSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadData(userId, false)
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(windowInsets = WindowInsets(0, 0, 0, 0),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
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
            isRefreshing = viewModel.profileState.refreshing || viewModel.postsState.refreshing,
            onRefresh = {viewModel.refresh()},
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 12.dp)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                state = lazyGridState
            ) {
                item(span = { GridItemSpan(3) }) {
                    if (viewModel.profileState.user != null) {
                        if (viewModel.profileState.isLoading) {
                            CircularProgressIndicator()
                        } else if (viewModel.profileState.user != null) {
                            Column {
                                ProfileTopSection(viewModel.profileState.user!!, navController)
                                Spacer(Modifier.height(12.dp))
                                Button(
                                    onClick = {
                                        if (viewModel.profileState.user!!.following) {
                                            viewModel.unfollowAccount(viewModel.profileState.user!!.id)
                                        } else {
                                            viewModel.followAccount(viewModel.profileState.user!!.id)
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    contentPadding = PaddingValues(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (viewModel.profileState.user!!.following) {
                                            MaterialTheme.colorScheme.secondaryContainer
                                        } else {
                                            MaterialTheme.colorScheme.primary
                                        },
                                        contentColor = if (viewModel.profileState.user!!.following) {
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
                                                if (viewModel.profileState.user!!.following) {
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

    if (showShareSheet && viewModel.profileState.user != null) {
        ModalBottomSheet(
            onDismissRequest = {
                showShareSheet = false
            }, sheetState = shareSheetState
        ) {
            ShareSheetComposable(viewModel.profileState.user!!)
        }
    }

}

