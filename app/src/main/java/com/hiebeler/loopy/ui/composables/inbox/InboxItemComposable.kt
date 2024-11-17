package com.hiebeler.loopy.ui.composables.inbox

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hiebeler.loopy.R
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.Notification
import com.hiebeler.loopy.utils.Navigate
import com.hiebeler.loopy.utils.TimeAgo
import sv.lib.squircleshape.SquircleShape

@Composable
fun InboxItemComposable(notification: Notification, navController: NavController) {
    val text = when (notification.type) {
        "new_follower" -> {
            " " + stringResource(R.string.followed_you)
        }

        "video.comment" -> {
            " " + stringResource(R.string.commented_on_video)
        }

        "video.like" -> {
            " " + stringResource(R.string.liked_your_post)
        }

        else -> {
            ""
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
        Navigate.navigate(
            "profile_screen/" + notification.actor.id, navController
        )
    }) {
        AsyncImage(
            model = notification.actor.avatar,
            contentDescription = "",
            modifier = Modifier
                .size(45.dp)
                .clip(shape = SquircleShape()),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(12.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = notification.actor.username, fontWeight = FontWeight.Bold)

                Text(text = text, overflow = TextOverflow.Ellipsis)
            }

            Text(
                text = TimeAgo.convertTimeToText(notification.createdAt),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        notification.videoThumbnail.let { thumbnail ->
            Spacer(modifier = Modifier.weight(1f))
            AsyncImage(
                model = thumbnail,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(36.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(4.dp))
            )
        }
    }
}