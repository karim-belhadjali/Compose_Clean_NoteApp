package com.main.noteapp.feature_note.domain.util

object Constants {



    //ENTITIES TABLE NAMES
    const val NOTE_TABLE_NAME= "notes"
    const val DELETED_NOTES_TABLE_NAME= "locally_deleted_note_ids"


    // ROOM AND RETROFIT CONSTANTS
    const val DATABASE_NAME= "note_database"
    const val BASE_URL= "http://10.0.2.2:8002"

    // RESPONSES MESSAGES CONSTANTS
    const val COULDNT_REACH_INTERNET_ERROR= "Couldn't connect to the servers. Check your internet connection"
    const val FILL_ALL_VALUES_ERROR= "Please fill out all the fields"

    const val OWNER_ADDED= "Successfully added owner to note "
    const val UNKNOWN_ERROR= "An unknown error has occured"
    const val NOTE_NOT_FOUND= "Note not found"
    const val NOTE__WAS_SUCCESSFULLY_DELETED= "Note was successfully deleted"




    // COLORS AND OTHER CONSTANTS
    const val DEFAULT_NOTE_COLOR= "FFA500"
}