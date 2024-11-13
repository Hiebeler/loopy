package com.hiebeler.loopy.ui.composables.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.utils.Navigate
import sv.lib.squircleshape.SquircleShape

@Composable
fun UserRow(user: Account, navController: NavController) {

    Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
        Navigate.navigate(
            "profile_screen/" + user.id, navController
        )
    }) {
        AsyncImage(
            model = user.avatar,
            contentDescription = "",
            modifier = Modifier
                .size(45.dp)
                .clip(shape = SquircleShape()),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(12.dp))

        Text(text = user.username)
    }
}