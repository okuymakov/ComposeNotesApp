package com.example.notes.ui.notes

import com.example.notes.domain.models.Note
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class NotesState (
    val notes: ImmutableList<Note> = persistentListOf()
)