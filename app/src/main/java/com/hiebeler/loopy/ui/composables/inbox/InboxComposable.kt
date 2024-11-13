package com.hiebeler.loopy.ui.composables.inbox

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun InboxComposable(
    navController: NavController,
    viewModel: InboxViewModel = hiltViewModel(key = "inbox-viewmodel-key")
) {
    Scaffold { padding ->
        Box(modifier = Modifier.padding(padding)) {


            Text("Inbox")
        }
    }
}

