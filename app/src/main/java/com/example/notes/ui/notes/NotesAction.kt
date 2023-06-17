package com.example.notes.ui.notes

sealed class NotesAction {
    data class DeleteNote(val id: Long) : NotesAction()
}