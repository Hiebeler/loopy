package com.hiebeler.loopy.ui.composables.profile.following

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hiebeler.loopy.R
import com.hiebeler.loopy.ui.composables.profile.UserRow

@Composable
fun FollowingComposable(
    userId: String,
    navController: NavController,
    viewModel: FollowingViewModel = hiltViewModel(key = "following-viewmodel-$userId-key")
) {

    LaunchedEffect(Unit) {
        viewModel.loadData(userId, false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp)
    ) {
        LazyColumn {
            item {
                Text(
                    text = stringResource(R.string.following),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )
            }

            viewModel.followerState.followers?.let { followers ->
                items(followers.data) { user ->
                    UserRow(user, navController)
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}