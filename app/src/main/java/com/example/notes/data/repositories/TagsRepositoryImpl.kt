package com.example.notes.data.repositories

import com.example.notes.data.mappers.toTag
import com.example.notes.data.mappers.toTagRealm
import com.example.notes.data.models.TagRealm
import com.example.notes.domain.models.Tag
import com.example.notes.domain.repositories.TagsRepository
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TagsRepositoryImpl(config: Configuration) : TagsRepository {
    private val dispatcher = Dispatchers.IO
    private val db = Realm.open(config)

    override suspend fun getTags() = withContext(dispatcher) {
        db.query<TagRealm>().asFlow()
            .map { resultsChange -> resultsChange.list.map { it.toTag() } }
    }

    override suspend fun getTagById(id: Long) = withContext(dispatcher) {
        db.query<TagRealm>("id == $0", id).find().first().toTag()
    }

    override suspend fun saveTag(tag: Tag) = withContext(dispatcher) {
        val isExist = db.query<TagRealm>("name == $0", tag.name).find().firstOrNull() != null
        if (!isExist) {
            db.write {
                this.copyToRealm(tag.toTagRealm())
            }.toTag()
        }
    }

    override suspend fun delete(id: Long) = withContext(dispatcher) {
        db.write {
            val res = this.query<TagRealm>("id == $0", id).find().first()
            delete(res)
        }
    }

    override suspend fun deleteAll(tags: List<Long>) = withContext(dispatcher) {
        db.write {
            val res = this.query<TagRealm>("id IN $0", tags).find()
            delete(res)
        }
    }
}