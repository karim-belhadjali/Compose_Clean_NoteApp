package com.main.noteapp.feature_note.domain.use_cases

import com.main.noteapp.feature_note.domain.model.InvalidNoteException
import com.main.noteapp.feature_note.domain.model.Note
import com.main.noteapp.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("the title of the note can't be empty.")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("the content of the note can't be empty.")
        }
        repository.insertNote(note)
    }
}