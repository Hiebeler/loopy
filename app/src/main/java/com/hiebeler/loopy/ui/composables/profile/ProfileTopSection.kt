package com.hiebeler.loopy.ui.composables.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hiebeler.loopy.R
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.ui.composables.profile.followers.FollowersComposable
import com.hiebeler.loopy.ui.composables.profile.following.FollowingComposable
import sv.lib.squircleshape.SquircleShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopSection(user: Account, navController: NavController) {
    val followerSheetState = rememberModalBottomSheetState()
    var showFollowerSheet by remember { mutableStateOf(false) }

    val followingSheetState = rememberModalBottomSheetState()
    var showFollowingSheet by remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = user.avatar,
            contentDescription = "",
            modifier = Modifier
                .size(112.dp)
                .clip(shape = SquircleShape()),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(24.dp))

        Column(
            Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                user.username,
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            if (user.bio.isNotBlank()) {
                Text(
                    user.bio,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        showFollowerSheet = true
                    }) {
                    Text(
                        user.followerCount.toString(),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        stringResource(R.string.followers),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable {
                        showFollowingSheet = true
                    }) {
                    Text(
                        user.followingCount.toString(),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        stringResource(R.string.following),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        user.postCount.toString(), fontSize = 22.sp, fontWeight = FontWeight.Bold
                    )
                    Text(
                        stringResource(R.string.posts),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }


    if (showFollowingSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showFollowingSheet = false
            }, sheetState = followingSheetState
        ) {
            FollowingComposable(user.id, navController)
        }
    }


    if (showFollowerSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showFollowerSheet = false
            }, sheetState = followerSheetState
        ) {
            FollowersComposable(user.id, navController)
        }
    }
}