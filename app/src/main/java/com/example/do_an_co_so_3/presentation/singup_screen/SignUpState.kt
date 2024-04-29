package com.example.do_an_co_so_3.presentation.singup_screen

    data class SignUpState(
        val isLoading: Boolean = false,
        val isSuccess: String? = "",
        val isError: String? = ""
    )