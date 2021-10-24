package com.main.noteapp.feature_login.presentation.util

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
}