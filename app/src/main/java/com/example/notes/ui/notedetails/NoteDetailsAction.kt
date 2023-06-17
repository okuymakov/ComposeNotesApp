package com.example.notes.ui.notedetails

import com.example.notes.domain.models.Tag

sealed class NoteDetailsAction {
    object SaveNote : NoteDetailsAction()
    data class EditContent(val content: String) : NoteDetailsAction()
    data class EditTitle(val title: String) : NoteDetailsAction()
    object DeleteNote : NoteDetailsAction()
    data class AddTag(val tag: Tag): NoteDetailsAction()
    data class DeleteTag(val tag: Tag): NoteDetailsAction()
}