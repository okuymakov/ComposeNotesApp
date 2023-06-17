package com.example.notes.ui.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.repositories.NotesRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

class NotesViewModel(private val repo: NotesRepository) : ViewModel() {
    var state by mutableStateOf(NotesState())

    init {
        viewModelScope.launch {
            repo.getNotes().collect { notes ->
                state = state.copy(notes = notes.toImmutableList())
            }
        }
    }

    fun onAction(action: NotesAction) {
        when (action) {
            is NotesAction.DeleteNote -> {
                viewModelScope.launch {
                    state =
                        state.copy(notes = (state.notes - state.notes.first { it.id == action.id }).toImmutableList())
                    repo.delete(action.id)
                }
            }
        }
    }
}