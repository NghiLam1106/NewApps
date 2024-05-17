package com.example.do_an_co_so_3.presentation.singup_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.do_an_co_so_3.data.AuthRepository
import com.example.do_an_co_so_3.data.Resource
import com.example.do_an_co_so_3.domain.model.Users
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    val _signUpState = Channel<SignUpState>()
    val signUpState = _signUpState.receiveAsFlow()

    fun registerUser(email: String, password: String, username: String) = viewModelScope.launch {
        repository.registerUser(email, password, username).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val userId = Firebase.auth.currentUser?.uid
                    userId?.let { uid ->
                        val user = Users(name = username, email = email, password = password)
                        Firebase.firestore.collection("User").document(uid).set(user)
                    }
                    _signUpState.send(SignUpState(isSuccess = "Đăng ký thành công"))
                }
                is Resource.Loading -> {
                    _signUpState.send(SignUpState(isLoading = true))
                }
                is Resource.Error -> {

                    _signUpState.send(SignUpState(isError = result.message))
                }
            }
        }
    }
}