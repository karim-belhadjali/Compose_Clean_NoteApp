package com.main.noteapp.feature_login.presentation.login

import androidx.compose.ui.focus.FocusState

sealed class LoginRegisterEvent{
    data class EnteredEmail(val value: String): LoginRegisterEvent()
    data class EnteredPassword(val value: String): LoginRegisterEvent()
    object LoginUser: LoginRegisterEvent()
}