package com.main.noteapp.feature_login.presentation.login

import FaIcons
import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.guru.fontawesomecomposelib.FaIcon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import com.guru.fontawesomecomposelib.FaIcon
import com.main.noteapp.feature_login.presentation.login.components.HorizontalDottedProgressBar
import com.main.noteapp.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Login(
    navController: NavController,
    viewModel: LoginRegisterViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    var email = viewModel.emailState.value
    var password = viewModel.passwordState.value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        //TextFields

        var hasError by remember { mutableStateOf(false) }
        var passwordVisualTransformation by remember {
            mutableStateOf<VisualTransformation>(
                PasswordVisualTransformation()
            )
        }
        var loading by remember {
            mutableStateOf(false)
        }
        val passwordInteractionState = remember { MutableInteractionSource() }
        val emailInteractionState = remember { MutableInteractionSource() }

        LaunchedEffect(key1 = true) {
            viewModel.uiEventFlow.collectLatest { event ->
                when (event) {
                    is LoginRegisterViewModel.UiEvent.ShowSnackBar -> {
                        loading = false
                        scaffoldState.snackbarHostState.showSnackbar(event.message)
                    }
                    is LoginRegisterViewModel.UiEvent.LoggedIn -> {
                        loading = false
                        scaffoldState.snackbarHostState.showSnackbar(event.message)
                    }
                    is LoginRegisterViewModel.UiEvent.Loading -> {
                        loading = true
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            item { Spacer(modifier = Modifier.height(20.dp)) }
            //item { LottieWorkingLoadingView() }
            item {
                Text(
                    text = "Welcome To Note App",
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            item {
                Text(
                    text = "We have missed you , let's start by sign In!",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            item {

                OutlinedTextField(
                    value = email,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Mail,
                            contentDescription = "Mail"
                        )
                    },
                    maxLines = 1,
                    isError = hasError,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    label = { Text(text = "Email address") },
                    placeholder = { Text(text = "abc@gmail.com") },
                    onValueChange = {
                        viewModel.onEvent(LoginRegisterEvent.EnteredEmail(it.trim()))
                    },
                    interactionSource = emailInteractionState,
                )
            }

            item {
                OutlinedTextField(
                    value = password,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Lock,
                            contentDescription = "password"
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Visibility,
                            contentDescription = "visibilty",
                            tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                            modifier = Modifier.clickable(onClick = {
                                passwordVisualTransformation =
                                    if (passwordVisualTransformation != VisualTransformation.None) {
                                        VisualTransformation.None
                                    } else {
                                        PasswordVisualTransformation()
                                    }
                            })
                        )
                    },
                    maxLines = 1,
                    isError = hasError,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text(text = "Password") },
                    placeholder = { Text(text = "12233444") },
                    onValueChange = {
                        viewModel.onEvent(LoginRegisterEvent.EnteredPassword(it.trim()))
                    },
                    interactionSource = emailInteractionState,
                    visualTransformation = passwordVisualTransformation
                )
            }
            item {

                Button(
                    onClick = {
                        if (invalidInput(email, password)) {
                            hasError = true
                            loading = false
                        } else {
                            loading = false
                            hasError = false
                            viewModel.onEvent(LoginRegisterEvent.LoginUser)
                        }
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
                        Text(text = "Log In")
                    }
                }
            }
            item {
                Box(modifier = Modifier.padding(vertical = 16.dp)) {
                    Spacer(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray)
                    )
                    Text(
                        text = "Or use",
                        color = Color.LightGray,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .background(MaterialTheme.colors.background)
                            .padding(horizontal = 16.dp)
                    )
                }
            }
            item {
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Facebook,
                        contentDescription = "facebook",
                        tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                    )
                    Text(
                        text = "Sign in with Facebook",
                        style = MaterialTheme.typography.h6.copy(fontSize = 14.sp),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                }
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                OutlinedButton(
                    onClick = { /*TODO*/ }, modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.GTranslate,
                        contentDescription = "google",
                        tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                    )
                    Text(
                        text = "Sign in with Google",
                        style = MaterialTheme.typography.h6.copy(fontSize = 14.sp),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                val primaryColor = MaterialTheme.colors.primary
                val annotatedString = remember {
                    AnnotatedString.Builder("Don't have an account? Register")
                        .apply {
                            addStyle(style = SpanStyle(color = primaryColor), 23, 31)
                        }
                }
                Text(
                    text = annotatedString.toAnnotatedString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clickable(onClick = {}),
                    textAlign = TextAlign.Center
                )
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }

        }
    }
}


fun invalidInput(email: String, password: String) =
    email.isBlank() || password.isBlank()