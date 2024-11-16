package com.hiebeler.loopy.ui.composables.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.ui.composables.profile.followers.FollowersComposable
import sv.lib.squircleshape.SquircleShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopSection(user: Account, navController: NavController) {
    val followerSheetState = rememberModalBottomSheetState()
    var showFollowerSheet by remember { mutableStateOf(false) }

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = user.avatar,
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                    showFollowerSheet = true
                }) {
                Text(
                    "Follower",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    user.follower_count.toString(),
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
                    user.following_count.toString(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


    }

    Spacer(Modifier.height(24.dp))

    Text(user.name, fontWeight = FontWeight.Bold)

    if (user.bio.isNotBlank()) {
        Text(user.bio)
    }

    if (showFollowerSheet) {
        ModalBottomSheet(onDismissRequest = {
            showFollowerSheet = false
        }, sheetState = followerSheetState
        ) {
            FollowersComposable(user.id, navController)
        }
    }
}