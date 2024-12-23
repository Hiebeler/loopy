package com.hiebeler.loopy.ui.composables.post

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.utils.Navigate

@Composable
fun SmallPost(post: Post, navController: NavController, modifier: Modifier) {
    Box(modifier = modifier
        .aspectRatio(9f / 16f)
        .background(MaterialTheme.colorScheme.surfaceContainer)
        .clickable {
            Navigate.navigate("single_post/" + post.id, navController)
        }) {
        AsyncImage(
            model = post.media.thumbnail,
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        Row(
            Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.5f) // Semi-transparent black
                        )
                    )
                )
                .padding(6.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = "",
                modifier = Modifier.size(18.dp)
            )

            Text(post.likes.toString(), fontWeight = FontWeight.Bold)
        }
    }
}