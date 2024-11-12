package com.hiebeler.loopy.ui.composables.home

import android.app.ActionBar.LayoutParams
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.utils.Navigate
import sv.lib.squircleshape.SquircleShape

@Composable
fun HomeComposable(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(key = "home-viewmodel-key")
) {
    Scaffold { padding ->
        Box(modifier = Modifier.padding(padding)) {
            LazyColumn {
                items(viewModel.feedState.feed) { item ->
                    Post(item, navController)
                    Spacer(Modifier.height(18.dp))
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
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun Post(post: Post, navController: NavController) {

    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                setMediaItem(MediaItem.fromUri(post.media.srcUrl))
                prepare()
                playWhenReady = true
            }
    }

    // Release the player when done
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(Modifier.aspectRatio(9f / 16f)) {

        AsyncImage(
            model = post.media.thumbnail,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                    layoutParams = LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        Row (modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth().padding(18.dp)) {
            Box(Modifier.weight(1f).fillMaxHeight()) {
                Column (Modifier.align(Alignment.BottomStart)) {
                    Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                        Navigate.navigate(
                            "profile_screen/" + post.account.id, navController
                        )
                    }) {
                        AsyncImage(
                            model = post.account.avatar,
                            contentDescription = "",
                            modifier = Modifier
                                .size(45.dp)
                                .clip(shape = SquircleShape()),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.width(12.dp))
                        Text(post.account.username, fontWeight = FontWeight.Bold)
                    }

                    if (post.caption.isNotEmpty()) {
                        Text(post.caption)
                    }

                    Spacer(Modifier.height(12.dp))
                }
            }

            Box(Modifier.wrapContentWidth().padding(12.dp).fillMaxHeight()) {
                Column (Modifier.align(Alignment.BottomEnd), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder, contentDescription = "", modifier = Modifier.size(32.dp)
                    )
                    Text(post.likes.toString(), fontWeight = FontWeight.Bold)

                    Spacer(Modifier.height(12.dp))

                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder, contentDescription = "", modifier = Modifier.size(32.dp)
                    )
                    Text(post.likes.toString(), fontWeight = FontWeight.Bold)

                    Spacer(Modifier.height(12.dp))
                }
            }


        }


    }
}