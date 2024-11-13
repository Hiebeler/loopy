package com.hiebeler.loopy.ui.composables.profile.followers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hiebeler.loopy.ui.composables.profile.UserRow

@Composable
fun FollowersComposable(
    userId: String, navController: NavController, viewModel: FollowersViewModel = hiltViewModel(key = "follower-viewmodel-$userId-key")
) {

    LaunchedEffect(Unit) {
        viewModel.loadData(userId, false)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp)
    ) {
        if (viewModel.followerState.followers?.data?.isNotEmpty() == true) {
            LazyColumn {
                items(viewModel.followerState.followers!!.data) { user ->
                    UserRow(user, navController)
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}