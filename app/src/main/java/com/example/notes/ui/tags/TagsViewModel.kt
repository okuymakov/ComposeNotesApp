package com.example.notes.ui.tags

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.models.Tag
import com.example.notes.domain.repositories.TagsRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

class TagsViewModel(private val repo: TagsRepository) : ViewModel() {
    var state by mutableStateOf(TagsState())

    init {
        viewModelScope.launch {
            repo.getTags().collect { tags ->
                state = state.copy(tags = tags.toImmutableList())
            }
        }
    }

    fun onAction(action: TagsAction) {
        when (action) {
            TagsAction.CloseDialog -> state = state.copy(openDialog = false)
            TagsAction.OpenDialog -> state = state.copy(openDialog = true)
            is TagsAction.UpdateTagName -> state = state.copy(tagName = action.name)
            TagsAction.SaveTag -> viewModelScope.launch {
                if (state.tagName.isNotBlank()) {
                    repo.saveTag(Tag(name = state.tagName))
                }
            }
            is TagsAction.DeleteTag -> viewModelScope.launch {
                repo.delete(action.id)
            }
        }
    }

}