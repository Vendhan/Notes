package com.vendhan.notes.domain.interactor

import com.vendhan.notes.data.database.entity.NotesEntity
import com.vendhan.notes.domain.repository.NotesRepository
import javax.inject.Inject

class UpdateNotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) {
    suspend fun updateNotes(notes: NotesEntity) {
        notesRepository
            .updateNotes(notes = notes)
    }
}
