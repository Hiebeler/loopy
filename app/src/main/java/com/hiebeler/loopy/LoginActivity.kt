package com.hiebeler.loopy

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.hiebeler.loopy.ui.composables.login.LoginComposable
import com.hiebeler.loopy.ui.theme.LoopyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private var isLoadingAfterRedirect: Boolean by mutableStateOf(false)
    private var error: String by mutableStateOf("")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)


        setContent {
            val context = LocalContext.current
            val activity = context as? Activity

            LoopyTheme {
                BackHandler {
                    activity?.finishAffinity();
                }
                Scaffold { paddingValues ->
                    Column(Modifier.padding(paddingValues)) {

                    }
                    LoginComposable(isLoadingAfterRedirect, error)
                }
            }
        }
    }
}