package com.main.noteapp.feature_login.data.data_source.remote


import com.main.noteapp.feature_login.domain.model.requests.AccountRequest
import com.main.noteapp.feature_note.data.data_source.remote.responses.SimpleResponse
import retrofit2.Response
import okhttp3.ResponseBody

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApi {

    @POST("/register")
    suspend fun register(
        @Body registerRequest: AccountRequest
    ): Response<SimpleResponse>

    @POST("/login")
    suspend fun login(
        @Body loginRequest: AccountRequest
    ): Response<SimpleResponse>


    @GET("/get")
    suspend fun gett(): Response<ResponseBody>
}