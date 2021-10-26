package com.main.noteapp.feature_login.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.main.noteapp.feature_login.presentation.login.LoginRegisterEvent
import com.main.noteapp.feature_login.presentation.login.components.HorizontalDottedProgressBar
import com.main.noteapp.utils.Screen
import kotlinx.coroutines.flow.collectLatest


@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: LoginRegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val name = remember {
        mutableStateOf(TextFieldValue())
    }
    val email = viewModel.registerEmailState.value
    val password = viewModel.registerPasswordState.value
    val confirmPassword = viewModel.registerConfirmPasswordState.value
    val loading = viewModel.loadingState.value

    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val confirmPasswordErrorState = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()


    val primaryColor = MaterialTheme.colors.primary

    LaunchedEffect(key1 = true) {
        viewModel.uiEventFlow.collectLatest { event ->
            when (event) {
                is LoginRegisterViewModel.UiEvent.EmailEmpty -> {
                    emailErrorState.value = true
                    passwordErrorState.value = false
                    confirmPasswordErrorState.value = false
                }
                is LoginRegisterViewModel.UiEvent.PasswordEmpty -> {
                    emailErrorState.value = false
                    passwordErrorState.value = true
                    confirmPasswordErrorState.value = false
                }
                is LoginRegisterViewModel.UiEvent.ConfirmedPasswordEmpty -> {
                    emailErrorState.value = false
                    passwordErrorState.value = false
                    confirmPasswordErrorState.value = true
                }
                is LoginRegisterViewModel.UiEvent.PasswordsNotMatching -> {
                    emailErrorState.value = false
                    passwordErrorState.value = true
                    confirmPasswordErrorState.value = true
                }
                is LoginRegisterViewModel.UiEvent.Registred -> {
                    emailErrorState.value = false
                    passwordErrorState.value = false
                    confirmPasswordErrorState.value = false
                }
                is LoginRegisterViewModel.UiEvent.NotRegistred -> {
                    emailErrorState.value = true
                    passwordErrorState.value = true
                    confirmPasswordErrorState.value = true
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Green)) {
                    append("R")
                }
                withStyle(style = SpanStyle(color = primaryColor)) {
                    append("egistration")
                }
            },
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                if (emailErrorState.value) {
                    emailErrorState.value = false
                }
                viewModel.onEvent(LoginRegisterEvent.EnteredRegisterEmail(it))
            },

            modifier = Modifier.fillMaxWidth(),
            isError = emailErrorState.value,
            label = {
                Text(text = "Email*")
            },
        )
        if (emailErrorState.value) {
            Text(text = "Required", color = Color.Red)
        }
        Spacer(modifier = Modifier.size(16.dp))
        val passwordVisibility = remember { mutableStateOf(true) }
        OutlinedTextField(
            value = password,
            onValueChange = {
                if (passwordErrorState.value) {
                    passwordErrorState.value = false
                }
                viewModel.onEvent(LoginRegisterEvent.EnteredRegisterPassword(it))
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Password*")
            },
            isError = passwordErrorState.value,
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(
                        imageVector = if (passwordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "visibility",
                        tint = primaryColor
                    )
                }
            },
            visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (passwordErrorState.value) {
            Text(text = "Required", color = Color.Red)
        }

        Spacer(Modifier.size(16.dp))
        val cPasswordVisibility = remember { mutableStateOf(true) }
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                if (confirmPasswordErrorState.value) {
                    confirmPasswordErrorState.value = false
                }
                viewModel.onEvent(LoginRegisterEvent.EnteredConfirmedPassword(it))
            },
            modifier = Modifier.fillMaxWidth(),
            isError = confirmPasswordErrorState.value,
            label = {
                Text(text = "Confirm Password*")
            },
            trailingIcon = {
                IconButton(onClick = {
                    cPasswordVisibility.value = !cPasswordVisibility.value
                }) {
                    Icon(
                        imageVector = if (cPasswordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "visibility",
                        tint = primaryColor
                    )
                }
            },
            visualTransformation = if (cPasswordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (confirmPasswordErrorState.value) {
            Text(text = "Required", color = Color.Red)
        }
        Spacer(Modifier.size(16.dp))
        OutlinedButton(
            onClick = {
                viewModel.onEvent(LoginRegisterEvent.RegisterUser)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(50.dp)
                .clip(CircleShape)
        ) {
            if (loading) {
                HorizontalDottedProgressBar()
            } else {
                Text(text = "Register")
            }
        }
        Spacer(Modifier.size(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            TextButton(onClick = {
                navController.navigate(Screen.LoginScreen.route)
            }) {
                Text(text = "Login", color = primaryColor)
            }
        }
    }
}

