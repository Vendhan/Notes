package com.vendhan.notes.domain.interactor

import com.vendhan.notes.domain.repository.NotesRepository
import javax.inject.Inject

class DeleteNotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {
    suspend fun deleteNotes(id: Int) {
        notesRepository
            .removeNotes(id = id)
    }
}
