package com.example.do_an_co_so_3.presentation.news_navigator

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.do_an_co_so_3.R
import com.example.do_an_co_so_3.domain.model.Article
import com.example.do_an_co_so_3.navigation.Screens
import com.example.do_an_co_so_3.presentation.save_news.SaveNewScreen
import com.example.do_an_co_so_3.presentation.save_news.SaveNewsViewModel
import com.example.do_an_co_so_3.presentation.details.DetailsScreen
import com.example.do_an_co_so_3.presentation.details.DetailsViewModel
import com.example.do_an_co_so_3.presentation.home.HomeScreen
import com.example.do_an_co_so_3.presentation.home.HomeViewModel
import com.example.do_an_co_so_3.presentation.login_screen.SignInScreen
import com.example.do_an_co_so_3.presentation.profile.ProfileScreen
import com.example.do_an_co_so_3.presentation.search.SearchScreen
import com.example.do_an_co_so_3.presentation.search.SearchViewModel
import com.example.do_an_co_so_3.presentation.singup_screen.SignUpScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NewsNavigator() {



    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home),
            BottomNavigationItem(icon = R.drawable.ic_search),
            BottomNavigationItem(icon = R.drawable.ic_bookmark),
            BottomNavigationItem(icon = R.drawable.user)
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Screens.HomeScreen.route -> 0
        Screens.SearchScreen.route -> 1
        Screens.BookmarkScreen.route -> 2
        Screens.SignInScreen.route -> 3
        Screens.SignUpScreen.route -> 3
        Screens.ProfileScreen.route -> 3
        else -> 0
    }

    //Hide the bottom navigation when the user is in the details screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Screens.HomeScreen.route ||
        backStackState?.destination?.route == Screens.SearchScreen.route ||
        backStackState?.destination?.route == Screens.BookmarkScreen.route ||
        backStackState?.destination?.route == Screens.ProfileScreen.route ||
        backStackState?.destination?.route == Screens.SignInScreen.route ||
        backStackState?.destination?.route == Screens.SignUpScreen.route
    }


    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            NewsBottomNavigation(
                items = bottomNavigationItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navController,
                            route = Screens.HomeScreen.route
                        )

                        1 -> navigateToTab(
                            navController = navController,
                            route = Screens.SearchScreen.route
                        )

                        2 -> navigateToTab(
                            navController = navController,
                            route = Screens.BookmarkScreen.route
                        )

                        3 -> navigateToTab(
                            navController = navController,
                            route = routecheck(Screens.SignInScreen.route)
                        )
                    }
                }
            )
        }
    }) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Screens.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Screens.SearchScreen.route
                        )
                    },
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }
            composable(route = Screens.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController = navController)
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }
            composable(route = Screens.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                        DetailsScreen(
                            article = article,
                            event = viewModel::onEvent,
                            navigateUp = { navController.navigateUp() },
                            sideEffect = viewModel.sideEffect
                        )
                    }

            }
            composable(route = Screens.BookmarkScreen.route) {
                val viewModel: SaveNewsViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController = navController)
                SaveNewScreen(
                    state = state,
                ) { article ->
                    navigateToDetails(
                        navController = navController,
                        article = article
                    )
                }
            }
            composable(route = Screens.SignInScreen.route) {
                SignInScreen(navController)
            }
            composable(route = Screens.SignUpScreen.route) {
                SignUpScreen(navController)
            }
            composable(route = Screens.ProfileScreen.route) {
                ProfileScreen(navController)
            }
        }
    }
}

fun routecheck(route: String): String {
    val userId = Firebase.auth.currentUser?.uid
    return if (userId == null) {
         route
    }
    else {
         Screens.ProfileScreen.route
    }
}

@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToTab(
            navController = navController,
            route = Screens.HomeScreen.route
        )
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Screens.DetailsScreen.route
    )
}