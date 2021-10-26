package com.main.noteapp.utils

sealed class Screen(val route: String) {
    object NotesScreen: Screen("notes_screen")
    object AddEditNoteScreen: Screen("add_edit_note_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")

}