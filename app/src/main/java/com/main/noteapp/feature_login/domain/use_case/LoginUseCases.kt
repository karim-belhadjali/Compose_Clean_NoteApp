package com.main.noteapp.feature_login.domain.use_case

data class LoginUseCases(
    val login: LoginUseCase,
    val register: RegisterUseCase,
    val authenticateApi: AuthenticateApi,
    val isLoggedin: IsLoggedin,
    val logoutUseCase: LogoutUseCase
)
