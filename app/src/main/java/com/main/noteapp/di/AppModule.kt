package com.main.noteapp.di

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.main.noteapp.feature_login.data.data_source.remote.BasicAuthInterceptor
import com.main.noteapp.feature_login.data.data_source.remote.LoginApi
import com.main.noteapp.feature_login.data.repository.LoginRepositoryImpl
import com.main.noteapp.feature_login.domain.repository.LoginRepository
import com.main.noteapp.feature_login.domain.use_case.*
import com.main.noteapp.feature_login.domain.util.Constants.SHARED_PREF_NAME
import com.main.noteapp.feature_login.presentation.login.Login
import com.main.noteapp.feature_note.data.data_source.local.NoteDatabase
import com.main.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.main.noteapp.feature_note.domain.repository.NoteRepository
import com.main.noteapp.feature_note.domain.use_cases.*
import com.main.noteapp.feature_note.domain.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext app: Context) =
        app.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideBasicAuthInterceptor()= BasicAuthInterceptor()

    @Singleton
    @Provides
    fun provideLoginApi(
        basicAuthInterceptor: BasicAuthInterceptor
    ): LoginApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(basicAuthInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(LoginApi::class.java)

    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(api: LoginApi): LoginRepository {
        return LoginRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNote = GetNote(repository),
            getNotes = GetNotes(repository),
            addNote = AddNote(repository),
            deleteNote = DeleteNote(repository)
        )
    }

    @Provides
    @Singleton
    fun provideLoginUseCases(repository: LoginRepository,basicAuthInterceptor: BasicAuthInterceptor,sharedPreferences: SharedPreferences): LoginUseCases {
        return LoginUseCases(
            login = LoginUseCase(repository),
            register = RegisterUseCase(repository),
            authenticateApi = AuthenticateApi(basicAuthInterceptor,sharedPreferences),
            isLoggedin = IsLoggedin(sharedPreferences),
            logoutUseCase = LogoutUseCase(basicAuthInterceptor, sharedPreferences)
        )
    }
}
