package com.example.do_an_co_so_3

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.do_an_co_so_3.domain.usercase.app_entry.AppEntryUseCases
import com.example.do_an_co_so_3.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
): ViewModel() {
    private val _startDestination = mutableStateOf(Screens.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination

    init {
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
            if (shouldStartFromHomeScreen) {
                _startDestination.value = Screens.NewsNavigation.route
            }
            else {
                _startDestination.value = Screens.AppStartNavigation.route
            }
            delay(300)
        }.launchIn(viewModelScope)
    }
}