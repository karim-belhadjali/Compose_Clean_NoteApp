package com.main.noteapp.feature_login.domain.model.requests

data class AccountRequest(
    val email: String,
    val password: String
)