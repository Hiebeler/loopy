package com.hiebeler.loopy.common

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.hiebeler.loopy.R

sealed class Destinations(
    val route: String, val icon: ImageVector? = null, val activeIcon: ImageVector? = null
) {
    data object HomeScreen : Destinations(
        route = "home_screen", icon = Icons.Outlined.Home, activeIcon = Icons.Filled.Home
    )
    data object OwnProfileScreen : Destinations(
        route = "own_profile_screen", icon = Icons.Outlined.AccountCircle, activeIcon = Icons.Filled.AccountCircle
    )
    data object Profile : Destinations(
        route = "profile_screen/{userid}", icon = Icons.Outlined.Favorite, activeIcon = Icons.Filled.Favorite
    )

    data object Inbox : Destinations(
        route = "inbox_screen", icon = Icons.Outlined.MailOutline, activeIcon = Icons.Filled.MailOutline
    )

    data object Explore : Destinations(
        route = "explore_screen", icon = Icons.Outlined.Explore, activeIcon = Icons.Filled.Explore
    )
}