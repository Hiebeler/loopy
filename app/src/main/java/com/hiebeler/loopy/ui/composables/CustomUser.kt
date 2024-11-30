package com.hiebeler.loopy.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.hiebeler.loopy.R
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.utils.Navigate
import sv.lib.squircleshape.SquircleShape
import java.util.Locale

@Composable
fun CustomUser(user: Account, navController: NavController) {
    CustomUserPrivate(
        user, clickable = true, logoutButton = false, logout = {}, navController = navController
    )
}

@Composable
fun CustomUser(user: Account, logoutButton: Boolean = false, logout: () -> Unit = {}) {
    CustomUserPrivate(user, clickable =  false, logoutButton = logoutButton, logout = logout, navController = null)
}

@Composable
private fun CustomUserPrivate(
    user: Account,
    clickable: Boolean,
    logoutButton: Boolean,
    logout: () -> Unit,
    navController: NavController?
) {
    Row(modifier = Modifier
        .clickable {
            if (clickable) {
                Navigate.navigate("profile_screen/" + user.id, navController!!)
            }
        }
        .padding(horizontal = 12.dp, vertical = 8.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = user.avatar,
            contentDescription = "",
            modifier = Modifier
                .height(46.dp)
                .width(46.dp)
                .clip(SquircleShape())
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = user.username, lineHeight = 8.sp, fontWeight = FontWeight.Bold
                )

                Text(
                    text = " • " + String.format(
                        Locale.GERMANY, "%,d", user.followerCount
                    ) + " " + stringResource(id = R.string.followers),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary,
                    lineHeight = 8.sp
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        if (logoutButton) {
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .width(36.dp)
                    .clickable { logout() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}