package com.ahmed.instagramclone.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.ahmed.instagramclone.presentation.appnav.AppNavigatorScreen
import com.ahmed.instagramclone.presentation.loginregister.LoginScreen
import com.ahmed.instagramclone.presentation.loginregister.SignUpScreen

@Composable
fun NavGraph(startDestination: String){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        navigation(startDestination = Route.LoginScreen.route, route = Route.AppStartNavigation.route){

            composable(route = Route.LoginScreen.route){
                LoginScreen(navController)
            }
            composable(route = Route.SignUpScreen.route){
                SignUpScreen(navController)
            }
        }
        navigation(route = Route.AppMainNavigation.route, startDestination = Route.AppNavigatorScreen.route){
            composable(route = Route.AppNavigatorScreen.route){
                AppNavigatorScreen()
            }
        }

    }

}