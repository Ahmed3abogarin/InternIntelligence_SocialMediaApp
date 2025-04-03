package com.ahmed.instagramclone.presentation.appnav

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.presentation.appnav.components.AppBottomNavigation
import com.ahmed.instagramclone.presentation.home.HomeScreen
import com.ahmed.instagramclone.presentation.home.HomeViewModel
import com.ahmed.instagramclone.presentation.navgraph.Route
import com.ahmed.instagramclone.presentation.new_post.NewPost
import com.ahmed.instagramclone.presentation.search.SearchScreen
import com.ahmed.instagramclone.presentation.profile.ProfileScreen
import com.ahmed.instagramclone.presentation.reels.ReelsScreen
import com.ahmed.instagramclone.presentation.search.SearchViewModel

@Composable
fun AppNavigatorScreen() {
    val bottomItems = remember {
        mutableListOf(
            BottomNavigationItem(icon = R.drawable.ic_home, color = Color.Cyan),
            BottomNavigationItem(icon = R.drawable.ic_search, color = Color.Green),
            BottomNavigationItem(icon = R.drawable.ic_new_post, color = Color.Magenta),
            BottomNavigationItem(icon = R.drawable.ic_reels, color = Color.Green),
            BottomNavigationItem(icon = R.drawable.ic_tags_outlined, color = Color.Magenta),
        )
    }

    val searchViewmodel: SearchViewModel = hiltViewModel()
    val homeViewmodel: HomeViewModel = hiltViewModel()



    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.NewScreen.route -> 2
            Route.ReelsScreen.route -> 3
            Route.ProfileScreen.route -> 4
            else -> 0
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AppBottomNavigation(
                items = bottomItems,
                selectedIndex = selectedItem,
                onItemClicked = { index ->
                    when (index) {
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
                            route = Route.NewScreen.route
                        )

                        3 -> navigateToTab(
                            navController = navController,
                            route = Route.ReelsScreen.route
                        )

                        4 -> navigateToTab(
                            navController = navController,
                            route = Route.ProfileScreen.route
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
        ) {
            composable(Route.HomeScreen.route) {
                HomeScreen(homeViewmodel.state.value)
            }
            composable(Route.SearchScreen.route) {
                SearchScreen(searchViewmodel.state.value, event = searchViewmodel::onEvent)
            }
            composable(Route.NewScreen.route) {
                NewPost()
            }
            composable(Route.ReelsScreen.route) {
                ReelsScreen()
            }
            composable(Route.ProfileScreen.route) {
                ProfileScreen()
            }

        }

    }


}

private fun navigateToTab(navController: NavController, route: String) {
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
    @DrawableRes val icon: Int,
    val color: Color,
    val size: IntSize = IntSize(0, 0),
    val offset: Offset = Offset(0f, 0f),
    )
