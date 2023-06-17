package com.example.notes.data.mappers

import com.example.notes.data.models.NoteRealm
import com.example.notes.data.models.TagRealm
import com.example.notes.domain.models.Note
import com.example.notes.domain.models.Tag
import io.realm.kotlin.ext.realmListOf
import kotlinx.collections.immutable.toImmutableList

fun NoteRealm.toNote(): Note {
    return Note(
        id = id,
        title = title,
        createdAt = createdAt,
        content = content,
        color = color,
        tags = tags.map { it.toTag() }.toImmutableList()
    )
}

fun Note.toNoteRealm(): NoteRealm {
    val note = this
    return NoteRealm().apply {
        id = note.id
        title = note.title
        createdAt = note.createdAt
        content = note.content
        color = note.color
        tags = realmListOf(*note.tags.map { it.toTagRealm() }.toTypedArray())
    }
}

fun TagRealm.toTag(): Tag {
    return Tag(id = id, name = name)
}

fun Tag.toTagRealm(): TagRealm {
    val tag = this
    return TagRealm().apply {
        id = tag.id
        name = tag.name
    }
}