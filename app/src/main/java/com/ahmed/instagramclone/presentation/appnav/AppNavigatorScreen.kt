package com.ahmed.instagramclone.presentation.appnav

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.model.StoryWithAuthor
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.presentation.appnav.components.AppBottomNavigation
import com.ahmed.instagramclone.presentation.chat.ChatScreen
import com.ahmed.instagramclone.presentation.chat.ChatViewModel
import com.ahmed.instagramclone.presentation.comments.CommentsScreen
import com.ahmed.instagramclone.presentation.comments.CommentsViewModel
import com.ahmed.instagramclone.presentation.details.PostDetails
import com.ahmed.instagramclone.presentation.edit.EditScreen
import com.ahmed.instagramclone.presentation.edit.EditViewModel
import com.ahmed.instagramclone.presentation.explore.ExploreScreen
import com.ahmed.instagramclone.presentation.explore.ExploreViewModel
import com.ahmed.instagramclone.presentation.followers.FollowersScreen
import com.ahmed.instagramclone.presentation.followers.FollowersViewModel
import com.ahmed.instagramclone.presentation.home.HomeScreen
import com.ahmed.instagramclone.presentation.home.HomeViewModel
import com.ahmed.instagramclone.presentation.messages.MessageViewModel
import com.ahmed.instagramclone.presentation.messages.MessagesScreen
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
import com.ahmed.instagramclone.presentation.story.StoryCreateViewModel
import com.ahmed.instagramclone.presentation.story.StoryScreen
import com.ahmed.instagramclone.presentation.user.UserScreen
import com.ahmed.instagramclone.presentation.user.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
    val commentVM: CommentsViewModel = hiltViewModel()


    val context = LocalContext.current
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
    val isBottomNavVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.ExploreScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.NewScreen.route ||
                backStackState?.destination?.route == Route.ReelsScreen.route ||
                backStackState?.destination?.route == Route.ProfileScreen.route ||
                backStackState?.destination?.route == Route.PostDetails.route
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomNavVisible) {
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
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        var showBottomDialog by remember { mutableStateOf(false) }
        var postId by remember { mutableStateOf("") }
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {

            composable(Route.HomeScreen.route) {

                if (showBottomDialog && postId.isNotEmpty()) {
                    CommentsScreen(
                        state = commentVM.state.value,
                        postId = postId,
                        commentsViewModel = commentVM,
                        sheetState = sheetState,
                        onDismiss = { showBottomDialog = false })

                }

                HomeScreen(
                    homeViewmodel.state.value,
                    navigateToStory = { navController.navigate(Route.StoryCreateScreen.route) },
                    storyState = homeViewmodel.stories.value,
                    navigateToUserStory = { story ->
                        navigateToStory2(navController, story)

                    },
                    currentUserId = homeViewmodel.currentUserId,
                    event = homeViewmodel::onEvent,
                    onCommentClicked = { id ->
                        showBottomDialog = true
                        postId = id
                    },
                    navigateToMessages = {
                        navController.navigate(Route.MessagesScreen.route)
                    }
                )
            }
            composable(Route.ExploreScreen.route) {
                ExploreScreen(
                    state = exploreViewModel.state.value,
                    navigateToSearch = { navController.navigate(Route.SearchScreen.route) },
                    navigateToPostDetails = { post -> navigateToPostDetails(navController, post) })
            }
            composable(Route.PostDetails.route) {

                if (showBottomDialog && postId.isNotEmpty()) {
                    CommentsScreen(
                        state = commentVM.state.value,
                        postId = postId,
                        commentsViewModel = commentVM,
                        sheetState = sheetState,
                        onDismiss = { showBottomDialog = false })

                }

                navController.previousBackStackEntry?.savedStateHandle?.get<PostWithAuthor>("post")
                    ?.let { post ->
                        PostDetails(
                            post,
                            navigateToUp = { navController.navigateUp() },
                            currentUserId = homeViewmodel.currentUserId,
                            event = homeViewmodel::onEvent,
                            onCommentClicked = { id ->
                                showBottomDialog = true
                                postId = id
                            }
                        )
                    }
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
                ProfileScreen(
                    state = profileViewModel.state.value,
                    posts = profileViewModel.postsState.value,
                    navigateToUserStory = {},
                    navigateToEdit = { navController.navigate(Route.EditInfoScreen.route) },
                    navigateToFollowers = { list, text ->
                        navigateToFollowers(navController, list, text)
                    },
                    navigateToDetails = { post ->
                        navigateToPostDetails(navController, post)
                    }
                )
            }
            composable("userScreen/{user_id}") {
                val userViewModel: UserViewModel = hiltViewModel()


                UserScreen(
                    isFollowing = userViewModel.isFollowing,
                    state = userViewModel.state.value,
                    posts = userViewModel.postsState.value,
                    navigateToUp = { navController.navigateUp() },
                    event = userViewModel::onEvent,
                    navigateToUserStory = { id ->
                        navigateToStory(navController, id)
                    },
                    navigateToChat = { user ->
                        navigateToChat(navController, user)
                    },
                    navigateToFollowers = { ids, header ->
                        navigateToFollowers(navController, ids, header)

                    },
                    navigateToDetails = { post ->
                        navigateToPostDetails(navController, post)
                    }
                )

            }

            composable(Route.StoryCreateScreen.route) {
                StoryCreateScreen(storyCreateViewModel)
            }

            composable(Route.StoryScreen.route) {

                val story =
                    navController.previousBackStackEntry?.savedStateHandle?.get<StoryWithAuthor>("story")

                story?.let {
                    StoryScreen(
                        story,
                        navigateToUser = { id ->
                            navigateToUserDetails(navController = navController, userId = id)
                        },
                        navigateToUp = { navController.navigateUp() })
                }


//                StoryScreen(storyViewModel.state.value)
            }

            composable(
                Route.ChatScreen.route,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { x -> x }, // Enter from right
                        animationSpec = tween(durationMillis = 600)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { x -> -x }, // Exit to left
                        animationSpec = tween(durationMillis = 600)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { x -> -x }, // Back-enter from left
                        animationSpec = tween(durationMillis = 600)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { x -> x }, // Back-exit to right
                        animationSpec = tween(durationMillis = 600)
                    )
                }
            ) {
                val chatVM: ChatViewModel = hiltViewModel()
                navController.previousBackStackEntry?.savedStateHandle?.get<User>("user")
                    ?.let { user ->
                        ChatScreen(
                            user,
                            chatVM.state.value,
                            event = chatVM::onEvent,
                            navigateToUser = { id -> navigateToUserDetails(navController, id) },
                            navigateUp = { navController.navigateUp() },
                            showToast = {
                                Toast.makeText(
                                    context,
                                    "This feature is not available yet",
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                    }

            }

            composable("followersScreen/{ids}/{title}") {
                val followersVM: FollowersViewModel = hiltViewModel()
                FollowersScreen(
                    text = followersVM.text.value,
                    state = followersVM.state,
                    navigateToUser = { userId -> navigateToUserDetails(navController, userId) },
                    navigateToUp = { navController.navigateUp() })
            }

            composable(
                Route.MessagesScreen.route,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { x -> x }, // Enter from right
                        animationSpec = tween(durationMillis = 600)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { x -> x }, // Exit to left
                        animationSpec = tween(durationMillis = 600)
                    )
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { x -> -x }, // Back-enter from left
                        animationSpec = tween(durationMillis = 600)
                    )
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { x -> x }, // Back-exit to right
                        animationSpec = tween(durationMillis = 600)
                    )
                }
            ) {
                val messagesVM: MessageViewModel = hiltViewModel()
                MessagesScreen(
                    messageViewModel = messagesVM,
                    message = messagesVM.lastMessage.value,
                    state = messagesVM.state.value,
                    navigateToUser = { user -> navigateToChat(navController, user) },
                    navigateUp = { navController.navigateUp() })
            }

            composable(Route.EditInfoScreen.route) {
                val editVM: EditViewModel = hiltViewModel()
                EditScreen(
                    state = editVM.state.value,
                    updateState = editVM.updateState.value,
                    event = editVM::onEvent,
                    navigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}

private fun navigateToChat(navController: NavController, user: User) {
    navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
    navController.navigate(route = Route.ChatScreen.route)

}

private fun navigateToStory2(navController: NavController, story: StoryWithAuthor) {
    navController.currentBackStackEntry?.savedStateHandle?.set("story", story)
    navController.navigate(Route.StoryScreen.route)

}

private fun navigateToStory(navController: NavController, userId: String) {
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

private fun navigateToFollowers(navController: NavController, idss: List<String>, header: String) {
    val ids = idss.joinToString(",")
    navController.navigate(route = "followersScreen/$ids/$header")

}

private fun navigateToPostDetails(navController: NavController, post: PostWithAuthor) {
    navController.currentBackStackEntry?.savedStateHandle?.set("post", post)
    navController.navigate(
        route = Route.PostDetails.route
    )

}

data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val color: Color,
    val size: IntSize = IntSize(0, 0),
    val offset: Offset = Offset(32f, 0f),
)
