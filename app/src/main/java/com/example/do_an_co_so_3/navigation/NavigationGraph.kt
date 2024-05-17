package com.example.do_an_co_so_3.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.do_an_co_so_3.presentation.login_screen.SignInScreen
import com.example.do_an_co_so_3.presentation.news_navigator.NewsNavigator
import com.example.do_an_co_so_3.presentation.onboarding.OnBoardingScreen
import com.example.do_an_co_so_3.presentation.onboarding.OnBoardingViewModel
import com.example.do_an_co_so_3.presentation.profile.ProfileScreen
import com.example.do_an_co_so_3.presentation.singup_screen.SignUpScreen

@Composable
fun NavigationGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Screens.AppStartNavigation.route,
            startDestination = Screens.OnBoardingScreen.route
        ) {
            composable(route = Screens.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }

        navigation(
            route = Screens.NewsNavigation.route,
            startDestination = Screens.NewsNavigatorScreen.route
        ) {
            composable(route = Screens.NewsNavigatorScreen.route){
                NewsNavigator()
            }
        }
    }
}