package com.example.do_an_co_so_3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.do_an_co_so_3.presentation.login_screen.SignInScreen
import com.example.do_an_co_so_3.presentation.singup_screen.SignUpScreen

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screens.SignInScreen.route
    ) {
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController)

        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController)

        }
    }
}