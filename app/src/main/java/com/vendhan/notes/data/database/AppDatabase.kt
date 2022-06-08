package com.vendhan.notes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vendhan.notes.data.database.dao.NotesDao
import com.vendhan.notes.data.database.entity.NotesEntity

@Database(entities = [NotesEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao
}
