package com.vendhan.notes.di

import android.content.Context
import androidx.room.Room
import com.vendhan.notes.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "Notes_DB"
    ).build()

    @Singleton
    @Provides
    fun providesNotesDao(appDatabase: AppDatabase) = appDatabase.notesDao()
}
