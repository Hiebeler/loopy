package com.hiebeler.loopy.ui.composables.profile.other_profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hiebeler.loopy.R
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.ui.composables.ButtonRowElement
import com.hiebeler.loopy.ui.composables.profile.UserRow
import com.hiebeler.loopy.utils.Navigate

@Composable
fun ShareSheetComposable (user: Account) {

    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp)
    ) {
        ButtonRowElement(icon = Icons.Outlined.ShoppingCart,
            text = "open in browser",
            onClick = {
                Navigate.openUrlInApp(context, user.url)
            })
    }
}