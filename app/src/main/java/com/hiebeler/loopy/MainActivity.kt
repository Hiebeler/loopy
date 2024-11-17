package com.hiebeler.loopy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.daniebeler.pfpixelix.di.HostSelectionInterceptorInterface
import com.hiebeler.loopy.common.Destinations
import com.hiebeler.loopy.domain.model.LoginData
import com.hiebeler.loopy.domain.usecases.GetCurrentLoginDataUseCase
import com.hiebeler.loopy.ui.composables.explore.ExploreComposable
import com.hiebeler.loopy.ui.composables.home.HomeComposable
import com.hiebeler.loopy.ui.composables.inbox.InboxComposable
import com.hiebeler.loopy.ui.composables.profile.own_profile.ProfileComposable
import com.hiebeler.loopy.ui.composables.profile.other_profile.OtherProfileComposable
import com.hiebeler.loopy.ui.theme.LoopyTheme
import com.hiebeler.loopy.utils.Navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var hostSelectionInterceptorInterface: HostSelectionInterceptorInterface

    @Inject
    lateinit var currentLoginDataUseCase: GetCurrentLoginDataUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        runBlocking {
            val loginData: LoginData? = currentLoginDataUseCase()
            if (loginData == null || loginData.accessToken.isBlank() || loginData.baseUrl.isBlank()) {
                gotoLoginActivity(this@MainActivity)
            } else {
                if (loginData.accessToken.isNotEmpty()) {
                    hostSelectionInterceptorInterface.setToken(loginData.accessToken)
                }
                if (loginData.baseUrl.isNotEmpty()) {
                    hostSelectionInterceptorInterface.setHost(
                        loginData.baseUrl.replace(
                            "https://", ""
                        )
                    )
                }
            }
        }

        setContent {
            LoopyTheme {
                val navController: NavHostController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(navController = navController) }) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavigationGraph(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

fun gotoLoginActivity(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    context.startActivity(intent)
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController,
        startDestination = Destinations.HomeScreen.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }) {
        composable(Destinations.HomeScreen.route) {
            HomeComposable(navController = navController)
        }
        composable(Destinations.OwnProfileScreen.route) {
            ProfileComposable(navController = navController)
        }

        composable(Destinations.Inbox.route) {
            InboxComposable(navController = navController)
        }

        composable(Destinations.Explore.route) {
            ExploreComposable(navController = navController)
        }

        composable(Destinations.Profile.route) { navBackStackEntry ->
            val uId = navBackStackEntry.arguments?.getString("userid")
            uId?.let { id ->
                OtherProfileComposable(navController, userId = id)
            }
        }
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        Destinations.HomeScreen,
        Destinations.Explore,
        Destinations.Inbox,
        Destinations.OwnProfileScreen,
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen: Destinations ->
            NavigationBarItem(icon = {
                if (currentRoute == screen.route) {
                    Icon(
                        imageVector = screen.activeIcon!!,
                        modifier = Modifier.size(32.dp),
                        contentDescription = ""
                    )
                } else {
                    Icon(
                        imageVector = screen.icon!!,
                        modifier = Modifier.size(32.dp),
                        contentDescription = ""
                    )
                }
            },
                selected = currentRoute == screen.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.inverseSurface, indicatorColor = Color.Transparent
                ),
                onClick = {
                    Navigate.navigateWithPopUp(screen.route, navController)
                })
        }
    }
}
