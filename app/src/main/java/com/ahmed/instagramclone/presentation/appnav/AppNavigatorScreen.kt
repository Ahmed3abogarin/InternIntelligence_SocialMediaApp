package com.ahmed.instagramclone.presentation.appnav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahmed.instagramclone.presentation.navgraph.Route
import com.ahmed.instagramclone.presentation.appnav.components.AppBottomNavigation
import com.ahmed.instagramclone.presentation.home.HomeScreen
import com.ahmed.instagramclone.presentation.search.SearchScreen
import com.ahmed.instagramclone.presentation.settings.SettingsScreen

@Composable
fun AppNavigatorScreen(){
    val bottomItems = remember { 
        listOf(
            BottomNavigationItem(icon = Icons.Default.Home, text = "Home"),
            BottomNavigationItem(icon = Icons.Default.Search, text = "Search"),
            BottomNavigationItem(icon = Icons.Default.Settings, text = "Settings"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember (key1 = backStackState){
        when(backStackState?.destination?.route){
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.SettingsScreen.route -> 2
            else -> 0
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomNavigation(
                items = bottomItems,
                selected = selectedItem,
                onItemClicked = {index ->
                    when(index){
                        0 -> navigateToTab(
                            navController = navController,
                            route = Route.HomeScreen.route
                        )

                        1 -> navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )

                        2 -> navigateToTab(
                            navController = navController,
                            route = Route.SettingsScreen.route
                        )
                    }
                }
            )
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ){
            composable(Route.HomeScreen.route){
                HomeScreen()
            }

            composable(Route.SearchScreen.route){
                SearchScreen()
            }

            composable(Route.SettingsScreen.route){
                SettingsScreen()
            }
        }

    }

    

}

private fun navigateToTab(navController: NavController,route: String){
    navController.navigate(route) {
        // every time we navigate to tab we wanna pop the backstack until we reach the home screen
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop =
                true // if you clicked multiple time on home screen icon that won't create a new instance of home screen each time
        }


    }
}

data class BottomNavigationItem(
    val icon: ImageVector,
    val text: String
)