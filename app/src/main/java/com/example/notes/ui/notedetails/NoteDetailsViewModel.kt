package com.example.notes.ui.notedetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.models.Tag
import com.example.notes.domain.repositories.NotesRepository
import com.example.notes.domain.repositories.TagsRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

class NoteDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val notesRepo: NotesRepository,
    private val tagsRepo: TagsRepository,
) : ViewModel() {

    var state by mutableStateOf(NoteDetailsState())
        private set

    var title by mutableStateOf("")
        private set

    var content by mutableStateOf("")
        private set

    init {
        val noteId = savedStateHandle.get<Long>("noteId")
        if (noteId != null && noteId != -1L) {
            viewModelScope.launch {
                val note = notesRepo.getNoteById(noteId)
//                title = note.title
//                content = note.content
                state = state.copy(note = note)
            }
        }
        viewModelScope.launch {
            tagsRepo.getTags().collect { allTags ->
                state = state.copy(allTags = (allTags - state.note.tags.toSet()).toImmutableList())
            }
        }
    }

    fun onAction(action: NoteDetailsAction) {
        when (action) {
            NoteDetailsAction.DeleteNote -> {}
            is NoteDetailsAction.SaveNote -> {
                viewModelScope.launch {
                    notesRepo.saveNote(state.note)
                }
            }
            is NoteDetailsAction.EditContent -> state =
                state.copy(note = state.note.copy(content = action.content))
            is NoteDetailsAction.EditTitle -> state =
                state.copy(note = state.note.copy(title = action.title))
            is NoteDetailsAction.AddTag -> {
                state = with(state) {
                    copy(
                        note = note.copy(tags = (note.tags + action.tag).toImmutableList()),
                        allTags = (allTags - action.tag).toImmutableList()
                    )
                }
            }
            is NoteDetailsAction.DeleteTag -> {
                state = with(state) {
                    copy(
                        note = note.copy(tags = (note.tags - action.tag).toImmutableList()),
                        allTags = (allTags + action.tag).toImmutableList()
                    )
                }
            }
        }
    }
}