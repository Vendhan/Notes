package com.vendhan.notes.domain.repository

import com.vendhan.notes.data.database.entity.NotesEntity
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun addNotes(notes: NotesEntity)

    suspend fun updateNotes(notes: NotesEntity)

    suspend fun getAllNotes(): Flow<List<NotesEntity>>

    suspend fun getNotesByID(id: Int): NotesEntity

    suspend fun removeNotes(id: Int)

    suspend fun getAllPinnedNotes(): Flow<List<NotesEntity>>

    suspend fun addNotesToPinned(id: Int)

    suspend fun removeNotesFromPinned(id: Int)
}
