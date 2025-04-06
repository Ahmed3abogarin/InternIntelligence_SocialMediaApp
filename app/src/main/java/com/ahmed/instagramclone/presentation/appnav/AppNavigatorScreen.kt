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
import com.ahmed.instagramclone.presentation.explore.ExploreScreen
import com.ahmed.instagramclone.presentation.explore.ExploreViewModel
import com.ahmed.instagramclone.presentation.home.HomeScreen
import com.ahmed.instagramclone.presentation.home.HomeViewModel
import com.ahmed.instagramclone.presentation.navgraph.Route
import com.ahmed.instagramclone.presentation.new_post.NewPostScreen
import com.ahmed.instagramclone.presentation.new_post.NewViewModel
import com.ahmed.instagramclone.presentation.profile.ProfileScreen
import com.ahmed.instagramclone.presentation.profile.ProfileViewModel
import com.ahmed.instagramclone.presentation.reels.ReelsScreen
import com.ahmed.instagramclone.presentation.reels.ReelsViewModel
import com.ahmed.instagramclone.presentation.search.SearchScreen
import com.ahmed.instagramclone.presentation.search.SearchViewModel
import com.ahmed.instagramclone.presentation.story.StoryCreateScreen
import com.ahmed.instagramclone.presentation.story.StoryScreen
import com.ahmed.instagramclone.presentation.story.StoryCreateViewModel
import com.ahmed.instagramclone.presentation.story.StoryViewModel
import com.ahmed.instagramclone.presentation.user.UserScreen
import com.ahmed.instagramclone.presentation.user.UserViewModel

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
    val newViewmodel: NewViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val reelsViewModel: ReelsViewModel = hiltViewModel()
    val exploreViewModel: ExploreViewModel = hiltViewModel()
    val storyCreateViewModel: StoryCreateViewModel = hiltViewModel()


    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.ExploreScreen.route -> 1
            Route.NewScreen.route -> 2
            Route.ReelsScreen.route -> 3
            Route.ProfileScreen.route -> 4
            else -> selectedItem
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
                            route = Route.ExploreScreen.route
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
                HomeScreen(
                    homeViewmodel.state.value,
                    navigateToStory = { navController.navigate(Route.StoryCreateScreen.route) })
            }
            composable(Route.ExploreScreen.route) {
                ExploreScreen(
                    state = exploreViewModel.state.value,
                    navigateToSearch = { navController.navigate(Route.SearchScreen.route) })
            }
            composable(Route.SearchScreen.route) {
                SearchScreen(
                    searchViewmodel.state.value,
                    event = searchViewmodel::onEvent,
                    navigateToUp = { navController.navigateUp() },
                    navigateToUser = { user ->
                        navigateToUserDetails(navController, userId = user.userId)
                    })
            }
            composable(Route.NewScreen.route) {
                NewPostScreen(state = newViewmodel.state.value, event = newViewmodel::onEvent)
            }
            composable(Route.ReelsScreen.route) {
                ReelsScreen(
                    reelsViewModel.state.value,
                    navigateToUser = { user ->
                        navigateToUserDetails(navController, userId = user.userId)
                    })
            }
            composable(Route.ProfileScreen.route) {
                ProfileScreen(state = profileViewModel.state.value!!, posts = profileViewModel.postsState.value)
            }
            composable("userScreen/{user_id}") {
                val userViewModel: UserViewModel = hiltViewModel()


                UserScreen(
                    isFollowing = userViewModel.isFollowing,
                    state = userViewModel.state.value,
                    navigateToUp = { navController.navigateUp() },
                    event = userViewModel::onEvent,
                    navigateToUserStory = { id ->
                        navigateToStory(navController, id)
                    }
                )

            }

            composable(Route.StoryCreateScreen.route) {
                StoryCreateScreen(storyCreateViewModel)
            }

            composable("storyScreen/{user_id}"){
                val storyViewModel: StoryViewModel = hiltViewModel()

                StoryScreen(storyViewModel.state.value)

            }
        }
    }
}
private fun navigateToStory(navController: NavController, userId: String){
    navController.navigate("storyScreen/$userId")

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

private fun navigateToUserDetails(navController: NavController, userId: String) {
    navController.navigate(route = "userScreen/$userId")

}

data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val color: Color,
    val size: IntSize = IntSize(0, 0),
    val offset: Offset = Offset(0f, 0f),
)
