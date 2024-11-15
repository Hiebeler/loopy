package com.hiebeler.loopy.ui.composables.inbox

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hiebeler.loopy.ui.composables.profile.UserRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxComposable(
    navController: NavController,
    viewModel: InboxViewModel = hiltViewModel(key = "inbox-viewmodel-key")
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(windowInsets = WindowInsets(0, 0, 0, 0),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
            title = {
                Text("Notifications", fontWeight = FontWeight.Bold)
            })
    }) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(viewModel.inboxState.notifications) {
                    UserRow(it.actor, navController)
                }
            }

            if (viewModel.inboxState.error.isNotEmpty()) {

                Text(viewModel.inboxState.error)
            }
        }
    }
}

