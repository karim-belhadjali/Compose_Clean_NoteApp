package com.main.noteapp.feature_login.presentation

import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.main.noteapp.feature_login.domain.use_case.LoginUseCases
import com.main.noteapp.feature_login.presentation.login.LoginRegisterEvent
import com.main.noteapp.feature_note.domain.util.Constants.UNKNOWN_ERROR
import com.main.noteapp.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginRegisterViewModel @Inject constructor(
    private val useCases: LoginUseCases
) : ViewModel() {

    private val _registerEmailState = mutableStateOf("")
    val registerEmailState: State<String> = _registerEmailState

    private val _registerPasswordState = mutableStateOf("")
    val registerPasswordState: State<String> = _registerPasswordState

    private val _registerConfirmPasswordState = mutableStateOf("")
    val registerConfirmPasswordState: State<String> = _registerConfirmPasswordState

    private val _loadingState = mutableStateOf(false)
    val loadingState: State<Boolean> = _loadingState

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

            is LoginRegisterEvent.EnteredRegisterEmail -> {
                _registerEmailState.value = event.value
            }
            is LoginRegisterEvent.EnteredRegisterPassword -> {
                _registerPasswordState.value = event.value
            }
            is LoginRegisterEvent.EnteredConfirmedPassword -> {
                _registerConfirmPasswordState.value = event.value
            }
            is LoginRegisterEvent.RegisterUser -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _uiEventFlow.emit(UiEvent.Loading)
                    when {
                        registerEmailState.value.isEmpty() -> {
                            _uiEventFlow.emit(UiEvent.EmailEmpty)
                        }
                        registerPasswordState.value.isEmpty() -> {
                            _uiEventFlow.emit(UiEvent.PasswordEmpty)
                        }
                        registerConfirmPasswordState.value.isEmpty() -> {
                            _uiEventFlow.emit(UiEvent.ConfirmedPasswordEmpty)
                        }
                        registerPasswordState.value != registerConfirmPasswordState.value -> {
                            _uiEventFlow.emit(UiEvent.PasswordsNotMatching)
                        }
                        else -> {
                            _loadingState.value=true
                            val result = useCases.register(
                                registerEmailState.value,
                                registerPasswordState.value
                            )
                            if (result.status == Status.SUCCES) {
                                _uiEventFlow.emit(UiEvent.Registred)
                                _loadingState.value=false
                            } else {
                                _uiEventFlow.emit(
                                    UiEvent.NotRegistred(
                                        result.message ?: UNKNOWN_ERROR
                                    )
                                )
                                _loadingState.value=false
                            }
                        }
                    }


                }
            }

        }
    }


    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        data class LoggedIn(val message: String) : UiEvent()
        object EmailEmpty : UiEvent()
        object PasswordEmpty : UiEvent()
        object ConfirmedPasswordEmpty : UiEvent()
        object PasswordsNotMatching : UiEvent()
        object Registred : UiEvent()
        data class NotRegistred(val message: String) : UiEvent()
        object Loading : UiEvent()

    }

}