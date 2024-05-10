package com.example.do_an_co_so_3.navigation

sealed class Screens(val route: String) {
    object SignInScreen: Screens(route = "SignIn_Screen")
    object SignUpScreen: Screens(route = "SignUp_Screen")
    object OnBoardingScreen : Screens(route = "onBoardingScreen")
    object HomeScreen : Screens(route = "homeScreen")
    object SearchScreen : Screens(route = "searchScreen")
    object BookmarkScreen : Screens(route = "bookMarkScreen")
    object DetailsScreen : Screens(route = "detailsScreen")
    object AppStartNavigation : Screens(route = "appStartNavigation")
    object NewsNavigation : Screens(route = "newsNavigation")
    object NewsNavigatiorScreen : Screens(route = "newsNavigatiorScreen")
}