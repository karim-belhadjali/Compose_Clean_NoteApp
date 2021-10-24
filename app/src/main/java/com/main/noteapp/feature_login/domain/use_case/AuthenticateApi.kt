package com.main.noteapp.feature_login.domain.use_case

import android.content.SharedPreferences
import com.main.noteapp.feature_login.data.data_source.remote.BasicAuthInterceptor
import com.main.noteapp.feature_login.domain.util.Constants.KEY_LOGGED_IN_EMAIL
import com.main.noteapp.feature_login.domain.util.Constants.KEY_PASSWORD
import com.main.noteapp.feature_login.domain.util.Constants.NO_PASSWORD

class AuthenticateApi(
    private val basicAuthInterceptor: BasicAuthInterceptor,
    private val sharedPreferences: SharedPreferences
) {

    suspend operator fun invoke(email: String, password: String) {
        basicAuthInterceptor.apply {
            this.email = email
            this.password = password
        }
        sharedPreferences.edit()
            .putString(KEY_LOGGED_IN_EMAIL, email)
            .putString(KEY_PASSWORD, password)
            .apply()
    }
}