package com.hiebeler.loopy.ui.composables.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hiebeler.loopy.R
import com.hiebeler.loopy.domain.model.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeComposable(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(key = "home-viewmodel-key")
) {
    Scaffold(topBar = {
        TopAppBar(windowInsets = WindowInsets(0, 0, 0, 0), title = {
            Text(stringResource(id = R.string.app_name), fontWeight = FontWeight.Bold)
        }, actions = {
            Row {

            }
        })
    }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            LazyColumn {
                items(viewModel.feedState.feed) { item ->
                    Post(item)
                }

                if(viewModel.feedState.isLoading) {
                    item {
                        CircularProgressIndicator()
                    }
                }
            }

            if (viewModel.feedState.error.isNotEmpty()) {
                Box {
                    Text(viewModel.feedState.error)
                }
            }
        }




        Text("home")
    }
}

@Composable
fun Post(post: Post) {
    Box() {

        AsyncImage(
            model = post.media.thumbnail,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(9f / 16f),
            contentScale = ContentScale.Crop
        )

        Text(post.id)
    }
}