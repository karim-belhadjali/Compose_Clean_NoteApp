package com.main.noteapp.feature_login.domain.util

object Constants {
    // INTERCEPTOR CONSTANTS
    val IGNORE_AUTH_URLS= listOf("/login","/register")

    //SHARED PREF CONSTANTS
    const val SHARED_PREF_NAME= "shared_pref"
    const val KEY_LOGGED_IN_EMAIL= "KEY_LOGGED_IN_EMAIL"
    const val KEY_PASSWORD= "KEY_PASSWORD"
    const val NO_EMAIL= "NO_EMAIL"
    const val NO_PASSWORD= "NO_PASSWORD"

    // RESPONSES MESSAGES CONSTANTS
    const val ACCOUNT_LOGGED_IN= "Successfully logged in"
    const val PASSWORDS_DO_NOT_MATCH= "The passwords do not match"
    const val ACCOUNT_REGISTRED= "Successfully registered an account"
}