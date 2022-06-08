package com.vendhan.notes.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vendhan.notes.data.database.entity.NotesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: NotesEntity)

    @Update
    suspend fun updateNotes(notes: NotesEntity)

    @Query("DELETE FROM NotesEntity WHERE id = :id")
    suspend fun removeNotes(id: Int)

    @Query("SELECT * FROM NotesEntity ORDER BY timeStamp DESC")
    fun getAllNotes(): Flow<List<NotesEntity>>

    @Query("SELECT * FROM NotesEntity WHERE id = :id")
    suspend fun getNotesByID(id: Int): NotesEntity

    @Query("SELECT * FROM NotesEntity WHERE isPinned = 1")
    fun getAllPinnedNotes(): Flow<List<NotesEntity>>

    @Query("UPDATE NotesEntity SET isPinned = 1 WHERE id = :id")
    suspend fun addNotesToPinned(id: Int)

    @Query("UPDATE NotesEntity SET isPinned = 0 WHERE id = :id")
    suspend fun removeNotesFromPin(id: Int)
}
