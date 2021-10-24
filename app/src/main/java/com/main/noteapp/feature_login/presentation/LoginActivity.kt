package com.main.noteapp.feature_login.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.main.noteapp.feature_login.presentation.login.Login
import com.main.noteapp.feature_login.presentation.util.Screen
import com.main.noteapp.ui.theme.ComposeCookBookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCookBookTheme() {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination =Screen.LoginScreen.route,
                    ){
                        composable(route = Screen.LoginScreen.route){
                            Login(navController)
                        }
                    }
                }
            }
        }
    }
}