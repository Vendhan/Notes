package com.vendhan.notes.data.repository

import com.vendhan.notes.data.database.dao.NotesDao
import com.vendhan.notes.data.database.entity.NotesEntity
import com.vendhan.notes.di.CoroutinesDispatchersModule
import com.vendhan.notes.domain.repository.NotesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val notesDao: NotesDao,
    @CoroutinesDispatchersModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : NotesRepository {

    override suspend fun getAllNotes(): Flow<List<NotesEntity>> {
        return withContext(ioDispatcher) {
            notesDao.getAllNotes()
        }
    }

    override suspend fun getNotesByID(id: Int): NotesEntity {
        return withContext(ioDispatcher) {
            notesDao.getNotesByID(id = id)
        }
    }

    override suspend fun removeNotes(id: Int) {
        withContext(ioDispatcher) {
            notesDao.removeNotes(id = id)
        }
    }

    override suspend fun getAllPinnedNotes(): Flow<List<NotesEntity>> {
        return withContext(ioDispatcher) {
            notesDao.getAllPinnedNotes()
        }
    }

    override suspend fun addNotes(notes: NotesEntity) {
        withContext(ioDispatcher) {
            notesDao.insertNotes(notes = notes)
        }
    }

    override suspend fun updateNotes(notes: NotesEntity) {
        withContext(ioDispatcher) {
            notesDao.updateNotes(notes = notes)
        }
    }

    override suspend fun addNotesToPinned(id: Int) {
        withContext(ioDispatcher) {
            notesDao.addNotesToPinned(id = id)
        }
    }

    override suspend fun removeNotesFromPinned(id: Int) {
        withContext(ioDispatcher) {
            notesDao.removeNotesFromPin(id = id)
        }
    }
}
