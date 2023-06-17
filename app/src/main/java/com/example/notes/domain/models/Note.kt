package com.example.notes.domain.models

import com.example.notes.core.generateNoteColor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.util.*


data class Note(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val title: String,
    val content: String,
    val tags: ImmutableList<Tag> = persistentListOf(),
    val createdAt: Long = Date().time,
    val color: Int = generateNoteColor()
)
