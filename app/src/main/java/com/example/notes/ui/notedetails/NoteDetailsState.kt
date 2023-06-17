package com.example.notes.ui.notedetails

import com.example.notes.domain.models.Note
import com.example.notes.domain.models.Tag
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


data class NoteDetailsState(
    val note: Note = Note(title = "", content = ""),
    val allTags: ImmutableList<Tag> = persistentListOf()
)