package com.example.do_an_co_so_3.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object AuthManager {
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    fun getCurrentUserEmail(): String? {
        return auth.currentUser?.email
    }
}