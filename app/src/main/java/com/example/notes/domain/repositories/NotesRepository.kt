package com.example.notes.domain.repositories

import com.example.notes.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Long) : Note
    suspend fun saveNote(note: Note): Note
    suspend fun deleteAll(notes: List<Long>)
    suspend fun delete(id: Long)
}