package com.example.do_an_co_so_3.navigation

import androidx.navigation.NamedNavArgument

sealed class Screens(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {
    object SignInScreen: Screens(route = "signinScreen")
    object SignUpScreen: Screens(route = "signupScreen")
    object ProfileScreen: Screens(route = "profileScreen")
    object OnBoardingScreen : Screens(route = "onBoardingScreen")
    object HomeScreen : Screens(route = "homeScreen")
    object SearchScreen : Screens(route = "searchScreen")
    object BookmarkScreen : Screens(route = "bookMarkScreen")
    object DetailsScreen : Screens(route = "detailsScreen")
    object AppStartNavigation : Screens(route = "appStartNavigation")
    object NewsNavigation : Screens(route = "newsNavigation")
    object NewsNavigatorScreen : Screens(route = "newsNavigator")
}