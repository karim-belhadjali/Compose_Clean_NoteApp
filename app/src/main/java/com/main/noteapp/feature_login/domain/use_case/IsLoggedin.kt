package com.main.noteapp.feature_login.domain.use_case

import android.content.SharedPreferences
import com.main.noteapp.feature_login.domain.util.Constants.KEY_LOGGED_IN_EMAIL
import com.main.noteapp.feature_login.domain.util.Constants.KEY_PASSWORD
import com.main.noteapp.feature_login.domain.util.Constants.NO_EMAIL
import com.main.noteapp.feature_login.domain.util.Constants.NO_PASSWORD

class IsLoggedin(
    private val sharedPreferences: SharedPreferences
) {
    suspend operator fun invoke(): Boolean {
        val curEmail = sharedPreferences.getString(KEY_LOGGED_IN_EMAIL, NO_EMAIL)
        val curPassword = sharedPreferences.getString(KEY_PASSWORD, NO_PASSWORD)

        return curEmail != NO_EMAIL && curPassword != NO_PASSWORD
    }
}