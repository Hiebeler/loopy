package com.hiebeler.loopy.ui.composables.profile.own_profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hiebeler.loopy.R
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.ui.composables.ButtonRowElement
import com.hiebeler.loopy.utils.Navigate

@Composable
fun PreferencesComposable (user: Account?, navController: NavController) {

    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .padding(bottom = 32.dp)
    ) {
        ButtonRowElement(icon = Icons.Outlined.Settings,
            text = "Settings",
            onClick = {
                Navigate.navigate("settings_screen", navController)
            })

        user?.let {
            ButtonRowElement(icon = Icons.Outlined.OpenInBrowser,
                text = stringResource(R.string.open_in_browser),
                onClick = {
                    Navigate.openUrlInApp(context, user.url)
                })
        }
    }
}