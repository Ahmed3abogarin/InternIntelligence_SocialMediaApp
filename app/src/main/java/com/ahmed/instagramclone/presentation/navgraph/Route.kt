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
    data object UserScreen: Route(route = "userScreen")

    data object AppStartNavigation: Route(route = "appLoginScreen")
    data object AppMainNavigation: Route(route = "appMainScreen")

    data object AppNavigatorScreen: Route(route = "appNavigatorScreen")
}