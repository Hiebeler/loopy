package com.hiebeler.loopy.ui.composables.post.single_post_page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hiebeler.loopy.ui.composables.post.LargePost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SinglePostComposable(
    postId: String,
    navController: NavController,
    viewModel: SinglePostViewModel = hiltViewModel(key = "single-post-key-$postId")
) {

    LaunchedEffect(Unit) {
        viewModel.getPost(postId)
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
            })
    }) { padding ->
        Box(Modifier.padding(padding)) {
            if (viewModel.postState.data != null) {
                LargePost(viewModel.postState.data!!, active = true, navController = navController)
            }
        }

    }

}