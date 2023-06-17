package com.example.notes.ui.tags

import com.example.notes.domain.models.Tag
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class TagsState(
    val tags: ImmutableList<Tag> = persistentListOf(),
    val openDialog: Boolean = false,
    val tagName: String = ""
)