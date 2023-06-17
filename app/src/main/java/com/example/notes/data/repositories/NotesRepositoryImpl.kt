package com.example.notes.data.repositories

import com.example.notes.data.mappers.toNote
import com.example.notes.data.mappers.toNoteRealm
import com.example.notes.data.models.NoteRealm
import com.example.notes.domain.models.Note
import com.example.notes.domain.repositories.NotesRepository
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NotesRepositoryImpl(config: Configuration) : NotesRepository {
    private val dispatcher = Dispatchers.IO
    private val db = Realm.open(config)

    override suspend fun getNotes() = withContext(dispatcher) {
        db.query<NoteRealm>().asFlow()
            .map { resultsChange -> resultsChange.list.map { it.toNote() } }
    }

    override suspend fun getNoteById(id: Long) = withContext(dispatcher) {
        db.query<NoteRealm>("id == $0", id).find().first().toNote()
    }

    override suspend fun saveNote(note: Note) = withContext(dispatcher) {
        db.write {
            this.copyToRealm(note.toNoteRealm(), updatePolicy = UpdatePolicy.ALL)
        }.toNote()
    }

    override suspend fun delete(id: Long) = withContext(dispatcher) {
        db.write {
            val res = this.query<NoteRealm>("id == $0", id).find().first()
            delete(res)
        }
    }

    override suspend fun deleteAll(notes: List<Long>) = withContext(dispatcher) {
        db.write {
            val res = this.query<NoteRealm>("id IN $0", notes).find()
            delete(res)
        }
    }
}