package com.ahmed.instagramclone.presentation.navgraph

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.ahmed.instagramclone.presentation.appnav.AppNavigatorScreen
import com.ahmed.instagramclone.presentation.login.LoginScreen
import com.ahmed.instagramclone.presentation.login.LoginViewModel
import com.ahmed.instagramclone.presentation.register.SignUpScreen

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            startDestination = Route.LoginScreen.route,
            route = Route.AppStartNavigation.route
        ) {

            composable(route = Route.LoginScreen.route) {
                LoginScreen(
                    event = loginViewModel::onEvent,
                    navigateToRegister = { navController.navigate(Route.SignUpScreen.route) },
                    navigateToMain = { navController.navigate(Route.AppMainNavigation.route) },
                    state = loginViewModel.state.value
                )
            }
            composable(route = Route.SignUpScreen.route,
                enterTransition = {
                    slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(600)
                    )
                },
                exitTransition = {
                    slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(600)
                    )
                }
            ) {
                SignUpScreen(
                    navigateUp = { navController.navigateUp() },
                    navigateToMain = { navController.navigate(Route.AppMainNavigation.route) })
            }
        }
        navigation(
            route = Route.AppMainNavigation.route,
            startDestination = Route.AppNavigatorScreen.route
        ) {
            composable(route = Route.AppNavigatorScreen.route) {
                AppNavigatorScreen()
            }
        }

    }

}