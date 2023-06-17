package com.example.notes.domain.repositories

import com.example.notes.domain.models.Tag
import kotlinx.coroutines.flow.Flow

interface TagsRepository {
    suspend fun getTags(): Flow<List<Tag>>
    suspend fun getTagById(id: Long): Tag
    suspend fun saveTag(tag: Tag)
    suspend fun delete(id: Long)
    suspend fun deleteAll(tags: List<Long>)
}