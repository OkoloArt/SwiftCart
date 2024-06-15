package com.example.swiftcart.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftcart.data.repository.DataStoreRepo
import com.example.swiftcart.navigation.BottomNavigationScreens
import com.example.swiftcart.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepo
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.Onboarding.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            repository.readOnBoardingState().collect { completed ->
                if (completed) {
                    repository.readHasLoggedInState().collect { loggedIn ->
                        _startDestination.value = if (loggedIn) {
                            BottomNavigationScreens.Home.route
                        } else {
                            Screen.Login.route
                        }
                        _isLoading.value = false
                    }
                } else {
                    _startDestination.value = Screen.Onboarding.route
                    _isLoading.value = false
                }
            }
        }

    }
}