package com.main.noteapp.feature_login.domain.use_case

import android.content.SharedPreferences
import com.main.noteapp.feature_login.data.data_source.remote.BasicAuthInterceptor
import com.main.noteapp.feature_login.domain.util.Constants
import com.main.noteapp.feature_login.domain.util.Constants.NO_EMAIL
import com.main.noteapp.feature_login.domain.util.Constants.NO_PASSWORD

class LogoutUseCase(
    val basicAuthInterceptor: BasicAuthInterceptor,
    val sharedPreferences: SharedPreferences
) {
    suspend operator fun invoke() {
        basicAuthInterceptor.apply {
            this.email = null
            this.password = null
        }
        sharedPreferences.edit()
            .putString(Constants.KEY_LOGGED_IN_EMAIL, NO_EMAIL)
            .putString(Constants.KEY_PASSWORD, NO_PASSWORD)
            .apply()
    }
}