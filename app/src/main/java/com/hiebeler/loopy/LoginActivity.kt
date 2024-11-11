package com.hiebeler.loopy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.hiebeler.loopy.ui.composables.LoginComposable
import com.hiebeler.loopy.ui.theme.LoopyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private var isLoadingAfterRedirect: Boolean by mutableStateOf(false)
    private var error: String by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)


        setContent {
            LoopyTheme {
                Scaffold { paddingValues ->
                    Column(Modifier.padding(paddingValues)) {

                    }
                    LoginComposable(isLoadingAfterRedirect, error)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val extras = intent.extras
        if (extras != null) {
            val baseUrl = extras.getString("base_url")
            val accessToken = extras.getString("access_token")
            if (baseUrl != null && accessToken != null) {
                //hostSelectionInterceptorInterface.setHost(baseUrl)
                //hostSelectionInterceptorInterface.setToken(accessToken)
                CoroutineScope(Dispatchers.Default).launch {
                 //   verifyToken(LoginData(baseUrl = baseUrl, accessToken = accessToken), true)
                }
            }
        }

        val url: Uri? = intent.data

        //Check if the activity was started after the authentication
        if (url == null || !url.toString().startsWith("pixelix-android-auth://callback")) return

        val code = url.getQueryParameter("code") ?: ""


        if (code.isNotEmpty()) {

            isLoadingAfterRedirect = true
            CoroutineScope(Dispatchers.Default).launch {
               // getTokenAndRedirect(code)
            }
        }
    }

   /* private suspend fun getTokenAndRedirect(code: String) {

        val loginData: LoginData? = getOngoingLoginUseCase()
        if (loginData == null) {
            error = "an unexpected error occured"
            isLoadingAfterRedirect = false
        } else {
            obtainTokenUseCase(loginData.clientId, loginData.clientSecret, code).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val newLoginData = loginData.copy(accessToken = result.data!!.accessToken)
                        updateLoginDataUseCase(newLoginData)
                        verifyToken(newLoginData, false)
                    }

                    is Resource.Error -> {
                        error = result.message ?: "Error"
                        isLoadingAfterRedirect = false
                    }

                    is Resource.Loading -> {
                        isLoadingAfterRedirect = true
                    }
                }
            }
        }
    }

    private suspend fun verifyToken(loginData: LoginData, updateToAuthV2: Boolean) {
        verifyTokenUseCase(loginData.accessToken).collect { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data == null) {
                        error = "an unexpected error occured"
                        isLoadingAfterRedirect = false
                        return@collect
                    }
                    val newLoginData = loginData.copy(
                        accountId = result.data.id,
                        username = result.data.username,
                        avatar = result.data.avatar,
                        displayName = result.data.displayname,
                        followers = result.data.followersCount,
                        loginOngoing = false
                    )
                    if (updateToAuthV2) {
                        newLoginDataUseCase.invoke(newLoginData)
                    }
                    finishLoginUseCase(newLoginData, newLoginData.accountId)

                    redirect()
                }

                is Resource.Error -> {
                    error = result.message ?: "Error"
                    isLoadingAfterRedirect = false
                }

                is Resource.Loading -> {
                    isLoadingAfterRedirect = true
                }
            }
        }
    }
*/
    private fun redirect() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        applicationContext.startActivity(intent)
    }
}