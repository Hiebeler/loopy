package com.hiebeler.loopy.ui.composables.profile.other_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.hiebeler.loopy.ui.composables.own_profile.OtherProfileViewModel
import com.hiebeler.loopy.ui.composables.post.SmallPost
import com.hiebeler.loopy.ui.composables.profile.followers.FollowersComposable
import sv.lib.squircleshape.SquircleShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherProfileComposable(
    navController: NavController,
    userId: String,
    viewModel: OtherProfileViewModel = hiltViewModel(key = "other-profile$userId")
) {

    val followerSheetState = rememberModalBottomSheetState()
    val shareSheetState = rememberModalBottomSheetState()
    var showFollowerSheet by remember { mutableStateOf(false) }
    var showShareSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadData(userId, false)
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(windowInsets = WindowInsets(0, 0, 0, 0),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
            title = {
                Text(viewModel.profileState.user?.username ?: "", fontWeight = FontWeight.Bold)
            },
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
                    showShareSheet = true
                }) {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert, contentDescription = ""
                    )
                }
            })
    }) { padding ->

        Column(modifier = Modifier.padding(padding)) {
            if (viewModel.profileState.user != null) {

                Column(
                    Modifier
                        .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .padding(24.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                    ) {

                        AsyncImage(
                            model = viewModel.profileState.user!!.avatar,
                            contentDescription = "",
                            modifier = Modifier
                                .size(90.dp)
                                .clip(shape = SquircleShape()),
                            contentScale = ContentScale.Crop
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
                                showFollowerSheet = true
                            }) {
                                Text(
                                    "Follower",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    viewModel.profileState.user!!.follower_count.toString(),
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "Following",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    viewModel.profileState.user!!.following_count.toString(),
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }


                    }

                    Spacer(Modifier.height(24.dp))

                    Text(viewModel.profileState.user!!.name, fontWeight = FontWeight.Bold)

                    if (viewModel.profileState.user?.bio != null) {
                        Text(viewModel.profileState.user!!.bio)
                    }
                }
            }

            if (viewModel.postsState.posts.isNotEmpty()) {
                LazyVerticalGrid(
                    modifier = Modifier.padding(12.dp),
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    items(viewModel.postsState.posts) { post ->
                        SmallPost(post)
                    }
                }
            }
        }
    }

    if (showFollowerSheet) {
        ModalBottomSheet(onDismissRequest = {
                showFollowerSheet = false
            }, sheetState = followerSheetState
        ) {
            FollowersComposable(userId, navController)
        }
    }

    if (showShareSheet && viewModel.profileState.user != null) {
        ModalBottomSheet(onDismissRequest = {
            showShareSheet = false
        }, sheetState = shareSheetState
        ) {
            ShareSheetComposable(viewModel.profileState.user!!)
        }
    }

}

