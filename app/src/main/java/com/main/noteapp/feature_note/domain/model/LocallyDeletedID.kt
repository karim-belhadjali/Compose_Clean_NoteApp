package com.main.noteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.main.noteapp.feature_note.domain.util.Constants.DELETED_NOTES_TABLE_NAME

@Entity(tableName = DELETED_NOTES_TABLE_NAME)
data class LocallyDeletedNoteID(
    @PrimaryKey(autoGenerate = false)
    val deletedNoteID: String
)