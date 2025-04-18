package com.ahmed.instagramclone.presentation.navgraph

sealed class Route(val route: String) {
    data object LoginScreen: Route(route = "loginScreen")
    data object SignUpScreen: Route(route = "signUpScreen")

    data object HomeScreen: Route(route = "homeScreen")
    data object ExploreScreen: Route(route = "exploreScreen")
    data object SearchScreen: Route(route = "searchScreen")
    data object NewScreen: Route(route = "newScreen")
    data object ReelsScreen: Route(route = "reelsScreen")
    data object ProfileScreen: Route(route = "profileScreen")
    data object UserScreen: Route(route = "userScreen/{user_id}")

    data object MessagesScreen: Route(route = "messagesScreen")


    data object StoryCreateScreen: Route(route = "storyCreateScreen")
    data object StoryScreen: Route(route = "storyScreen/{user_id}")

    data object PostDetails: Route(route = "postScreen")

    data object ChatScreen: Route(route = "chatScreen")
    data object EditInfoScreen: Route(route = "editInfoScreen")

    data object AppStartNavigation: Route(route = "appLoginScreen")
    data object AppMainNavigation: Route(route = "appMainScreen")

    data object AppNavigatorScreen: Route(route = "appNavigatorScreen")
}