package com.main.noteapp.feature_note.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.main.noteapp.feature_note.domain.model.LocallyDeletedNoteID
import com.main.noteapp.feature_note.domain.model.Note

@Database(
    entities = [Note::class,LocallyDeletedNoteID::class],
    version = 1.2.toInt()
)
@TypeConverters(Converters::class)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}