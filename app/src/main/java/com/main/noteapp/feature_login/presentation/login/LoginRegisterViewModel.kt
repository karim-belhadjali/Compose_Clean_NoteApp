package com.main.noteapp.feature_login.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.main.noteapp.feature_login.data.data_source.remote.LoginApi
import com.main.noteapp.feature_login.data.repository.LoginRepositoryImpl
import com.main.noteapp.feature_login.domain.model.requests.AccountRequest
import com.main.noteapp.feature_login.domain.repository.LoginRepository
import com.main.noteapp.feature_login.domain.use_case.LoginUseCases
import com.main.noteapp.feature_note.domain.util.Constants
import com.main.noteapp.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import com.main.noteapp.feature_note.presentation.add_edit_note.NoteTextFieldState
import com.main.noteapp.utils.Ressource
import com.main.noteapp.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginRegisterViewModel @Inject constructor(
    private val useCases: LoginUseCases
) : ViewModel() {


    private val _emailState = mutableStateOf("")
    val emailState: State<String> = _emailState

    private val _passwordState = mutableStateOf("")
    val passwordState: State<String> = _passwordState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    private val _uiEventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()


    fun onEvent(event: LoginRegisterEvent) {
        when (event) {
            is LoginRegisterEvent.EnteredEmail -> {
                _emailState.value = event.value
            }
            is LoginRegisterEvent.EnteredPassword -> {
                _passwordState.value = event.value
            }
            is LoginRegisterEvent.LoginUser -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _uiEventFlow.emit(UiEvent.Loading)
                    val result = useCases.login(_emailState.value, _passwordState.value)
                    if (result.status == Status.SUCCES) {
                        println(result.message)
                        _uiEventFlow.emit(UiEvent.LoggedIn(result.message ?: ""))
                    } else {
                        println(result.message)
                        _uiEventFlow.emit(
                            UiEvent.ShowSnackBar(
                                result.message ?: "Something went wrong with the servers"
                            )
                        )
                    }
                }
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        data class LoggedIn(val message: String) : UiEvent()
        object Loading : UiEvent()
    }

}