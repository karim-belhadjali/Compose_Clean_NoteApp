package com.main.noteapp.feature_note.data.data_source.remote.requests

data class OwnerRequest(
    val noteId: String,
    val newOwner: String
)