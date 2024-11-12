package com.hiebeler.loopy.ui.composables.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hiebeler.loopy.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileComposable(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(key = "own-profile-key")
) {
    Scaffold(topBar = {
        TopAppBar(windowInsets = WindowInsets(0, 0, 0, 0), title = {
            Text(stringResource(id = R.string.profile), fontWeight = FontWeight.Bold)
        })
    }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (viewModel.ownProfileState.user != null) {
                Text(viewModel.ownProfileState.user!!.username)
            }
        }
    }
}