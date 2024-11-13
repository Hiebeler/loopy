package com.hiebeler.loopy.common

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.hiebeler.loopy.R

sealed class Destinations(
    val route: String, val icon: ImageVector? = null, @StringRes val label: Int = R.string.home
) {
    data object HomeScreen : Destinations(
        route = "home_screen", icon = Icons.Outlined.Home, label = R.string.home
    )
    data object OwnProfileScreen : Destinations(
        route = "own_profile_screen", icon = Icons.Outlined.AccountCircle, label = R.string.profile
    )
    data object Profile : Destinations(
        route = "profile_screen/{userid}", icon = Icons.Outlined.Favorite
    )

    data object Inbox : Destinations(
        route = "inbox_screen", icon = Icons.Outlined.MailOutline, label = R.string.inbox
    )

}