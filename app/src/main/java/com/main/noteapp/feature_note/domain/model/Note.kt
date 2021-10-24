package com.main.noteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.main.noteapp.ui.theme.*
import java.util.*

@Entity()
data class Note(
    val title: String,
    val content: String,
    val date: Long,
    val owners: List<String>,
    val color: String,
    @Expose(deserialize = false, serialize = false)
    var isSynced: Boolean = false,
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString()
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message)