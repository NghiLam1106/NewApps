package com.example.do_an_co_so_3.navigation

sealed class Screens(val route: String) {
    object SignInScreen: Screens(route = "SignIn_Screen")
    object SignUpScreen: Screens(route = "SignUp_Screen")

}