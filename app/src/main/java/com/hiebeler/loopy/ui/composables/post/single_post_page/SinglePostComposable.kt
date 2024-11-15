package com.hiebeler.loopy.ui.composables.post.single_post_page

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SinglePostComposable (
    postId: String,
    viewModel: SinglePostViewModel = hiltViewModel(key = "single-post-key-$postId")
) {


}