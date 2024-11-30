package com.hiebeler.loopy.ui.composables.post

import android.app.ActionBar.LayoutParams
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.utils.Navigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sv.lib.squircleshape.SquircleShape


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargePost(
    _post: Post,
    active: Boolean,
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel(key = "postViewModel_${_post.id}")
) {

    val context = LocalContext.current

    val moreSheetState = rememberModalBottomSheetState()
    var showMoreSheet by remember { mutableStateOf(false) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(_post.media.srcUrl))
            prepare()
            playWhenReady = false
        }
    }

    LaunchedEffect(_post.id) {
        viewModel.updatePost(_post)
    }

    LaunchedEffect(active) {
        if (active) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    var showHeart by remember { mutableStateOf(false) }
    val scale = animateFloatAsState(if (showHeart) 1f else 0f, label = "heart animation")
    LaunchedEffect(showHeart) {
        if (showHeart) {
            delay(1000)
            showHeart = false
        }
    }

    viewModel.post?.let { post: Post ->

        Box(Modifier.aspectRatio(9f / 16f).pointerInput(Unit) {
            detectTapGestures(onDoubleTap = {
                CoroutineScope(Dispatchers.Default).launch {
                    viewModel.likePost(post.id)
                    showHeart = true
                }
            })
        }) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = Color(0xFFDD2E44),
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
                    .scale(scale.value)
                    .zIndex(100f)
            )

            AsyncImage(
                model = post.media.thumbnail,
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = false
                        layoutParams = LayoutParams(
                            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT
                        )
                    }
                }, modifier = Modifier.fillMaxSize()
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Column(Modifier.align(Alignment.BottomStart)) {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
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

                Box(
                    Modifier
                        .wrapContentWidth()
                        .padding(12.dp)
                        .fillMaxHeight()
                ) {
                    Column(
                        Modifier.align(Alignment.BottomEnd),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(onClick = {
                            showMoreSheet = true
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.MoreVert,
                                contentDescription = "",
                                modifier = Modifier.size(32.dp)
                            )
                        }


                        Spacer(Modifier.height(12.dp))
                        if (post.hasLiked) {
                            Icon(
                                imageVector = Icons.Rounded.Favorite,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable {
                                        Log.d("like", "unlike")
                                        viewModel.unlikePost(post.id)
                                    },
                                tint = Color(0xFFDD2E44)
                            )
                        } else {
                            Icon(imageVector = Icons.Rounded.FavoriteBorder,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable {
                                        Log.d("like", "like")
                                        viewModel.likePost(post.id)
                                    })
                        }
                        Text(post.likes.toString(), fontWeight = FontWeight.Bold)

                        Spacer(Modifier.height(12.dp))

                        Icon(
                            imageVector = Icons.Rounded.ChatBubbleOutline,
                            contentDescription = "",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(post.comments.toString(), fontWeight = FontWeight.Bold)

                        Spacer(Modifier.height(12.dp))
                    }
                }


            }


        }

        if (showMoreSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showMoreSheet = false
                }, sheetState = moreSheetState
            ) {
                MoreComposable(post)
            }
        }
    }
}