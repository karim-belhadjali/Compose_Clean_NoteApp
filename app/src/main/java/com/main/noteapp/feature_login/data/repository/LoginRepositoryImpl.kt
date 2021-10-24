package com.main.noteapp.feature_login.data.repository

import com.main.noteapp.feature_login.data.data_source.remote.LoginApi
import com.main.noteapp.feature_login.domain.model.requests.AccountRequest
import com.main.noteapp.feature_login.domain.repository.LoginRepository
import com.main.noteapp.feature_login.domain.util.Constants.ACCOUNT_LOGGED_IN
import com.main.noteapp.feature_note.domain.util.Constants.COULDNT_REACH_INTERNET_ERROR
import com.main.noteapp.utils.Ressource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class LoginRepositoryImpl constructor(
    private val loginApi: LoginApi
) : LoginRepository {

    override suspend fun login(email: String, password: String) = withContext(Dispatchers.IO) {
        try {
            val response = loginApi.login(AccountRequest(email, password))
            if (response.isSuccessful && response.body()!!.successful) {
                Ressource.success(ACCOUNT_LOGGED_IN)
            } else {
                Ressource.error(response.body()?.message ?: response.message(), null)
            }
        } catch (e: Exception) {
            Ressource.error(COULDNT_REACH_INTERNET_ERROR, null)
        }
    }


    override suspend fun register(email: String, password: String) = withContext(Dispatchers.IO) {
        try {
            val response = loginApi.register(AccountRequest(email, password))
            if (response.isSuccessful && response.body()!!.successful) {
                Ressource.success(response.body()?.message ?: response.message())
            } else {
                Ressource.error(response.body()?.message ?: response.message(), null)
            }
        } catch (e: Exception) {
            Ressource.error(COULDNT_REACH_INTERNET_ERROR, null)
        }
    }
}
