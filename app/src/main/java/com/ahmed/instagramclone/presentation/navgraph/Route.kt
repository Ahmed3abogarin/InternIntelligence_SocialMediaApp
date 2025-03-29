package com.ahmed.instagramclone.presentation.navgraph

sealed class Route(val route: String) {
    data object LoginScreen: Route(route = "loginScreen")
    data object SignUpScreen: Route(route = "signUpScreen")

    data object HomeScreen: Route(route = "signUpScreen")
    data object SearchScreen: Route(route = "searchScreen")
    data object SettingsScreen: Route(route = "settingsScreen")

    data object AppStartNavigation: Route(route = "appLoginScreen")
    data object AppMainNavigation: Route(route = "appMainScreen")

    data object AppNavigatorScreen: Route(route = "appNavigatorScreen")
}