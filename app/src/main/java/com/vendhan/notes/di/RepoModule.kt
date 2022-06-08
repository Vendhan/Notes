package com.vendhan.notes.di

import com.vendhan.notes.data.repository.NotesRepositoryImpl
import com.vendhan.notes.domain.repository.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun provideNotesRepoImpl(repository: NotesRepositoryImpl): NotesRepository
}
