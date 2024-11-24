package com.hiebeler.loopy.ui.composables.post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hiebeler.loopy.R
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.ui.composables.ButtonRowElement
import com.hiebeler.loopy.utils.Navigate
import com.hiebeler.loopy.utils.Share

@Composable
fun MoreComposable(post: Post) {

    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .padding(bottom = 32.dp)
    ) {
        ButtonRowElement(icon = Icons.Outlined.Share,
            text = "Share",
            onClick = {
                Share.shareText(context, post.url)
            })

        ButtonRowElement(icon = Icons.Outlined.OpenInBrowser,
            text = stringResource(R.string.open_in_browser),
            onClick = {
                Navigate.openUrlInApp(context, post.url)
            })
    }
}