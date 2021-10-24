package com.main.noteapp.feature_login.domain.repository

import com.main.noteapp.feature_login.data.data_source.remote.LoginApi
import com.main.noteapp.utils.Ressource
import javax.inject.Inject

interface LoginRepository {


    suspend fun login(email: String, password: String):Ressource<String>

    suspend fun register(email: String, password: String):Ressource<String>

}