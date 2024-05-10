package com.example.do_an_co_so_3.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.do_an_co_so_3.presentation.home.HomeScreen
import com.example.do_an_co_so_3.presentation.home.HomeViewModel
import com.example.do_an_co_so_3.presentation.search.SearchScreen
import com.example.do_an_co_so_3.presentation.login_screen.SignInScreen
import com.example.do_an_co_so_3.presentation.onboarding.OnBoardingScreen
import com.example.do_an_co_so_3.presentation.onboarding.OnBoardingViewModel
import com.example.do_an_co_so_3.presentation.search.SearchViewModel
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
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController)

        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController)

        }

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

//        navigation(
//            route = Screens.NewsNavigation.route,
//            startDestination = Screens.NewsNavigatiorScreen.route
//        ) {
//            composable(route = Screens.NewsNavigatiorScreen.route) {
//                val viewModel: HomeViewModel = hiltViewModel()
//                val articles = viewModel.news.collectAsLazyPagingItems()
//                HomeScreen(articles = articles, navigate = {})
//            }
//        }

        navigation(
            route = Screens.NewsNavigation.route,
            startDestination = Screens.NewsNavigatiorScreen.route
        ) {
            composable(route = Screens.NewsNavigatiorScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                SearchScreen(state = viewModel.state.value, event = viewModel::onEvent, navigate = {})
            }
        }
    }
}