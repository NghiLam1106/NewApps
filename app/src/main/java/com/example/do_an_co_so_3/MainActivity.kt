package com.example.do_an_co_so_3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.do_an_co_so_3.domain.usercase.AppEntryUseCases
import com.example.do_an_co_so_3.navigation.NavigationGraph
import com.example.do_an_co_so_3.presentation.onboarding.OnBoardingScreen
import com.example.do_an_co_so_3.presentation.onboarding.OnBoardingViewModel
import com.example.do_an_co_so_3.ui.theme.Do_an_co_so_3Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            Do_an_co_so_3Theme(
                dynamicColor = false
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                        val startDestination = viewModel.startDestination
                        NavigationGraph(startDestination = startDestination)
                    }
                }
            }
        }
    }
}

