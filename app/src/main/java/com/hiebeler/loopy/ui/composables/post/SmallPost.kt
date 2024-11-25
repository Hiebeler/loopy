package com.hiebeler.loopy.ui.composables.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hiebeler.loopy.domain.model.Post

@Composable
fun SmallPost(post: Post, modifier: Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(9f / 16f).background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        AsyncImage(
            model = post.media.thumbnail,
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        Row(Modifier.align(Alignment.BottomStart).padding(6.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = "",
                modifier = Modifier.size(18.dp)
            )

            Text(post.likes.toString(), fontWeight = FontWeight.Bold)
        }
    }
}