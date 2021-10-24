package com.main.noteapp.feature_note.data.data_source.remote

import com.main.noteapp.feature_login.domain.model.requests.AccountRequest
import com.main.noteapp.feature_note.data.data_source.remote.requests.DeleteNoteRequest
import com.main.noteapp.feature_note.data.data_source.remote.requests.OwnerRequest
import com.main.noteapp.feature_note.data.data_source.remote.responses.SimpleResponse
import com.main.noteapp.feature_note.domain.model.Note
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NoteApi {

    @POST("/addNote")
    suspend fun addNote(
        @Body note: Note
    ): Response<ResponseBody>

    @POST("/deleteNote")
    suspend fun deleteNote(
        @Body deleteNoteRequest: DeleteNoteRequest
    ): Response<ResponseBody>

    @POST("/addOwner")
    suspend fun addOwnerToNote(
        @Body addOwnerRequest: OwnerRequest
    ): Response<SimpleResponse>

    @GET("/getNotes")
    suspend fun getNotes(): Response<List<Note>>
}