package com.vendhan.notes.domain.interactor

import com.vendhan.notes.domain.repository.NotesRepository
import javax.inject.Inject

class UnPinNotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {
    suspend fun unPinNotes(id: Int) {
        notesRepository
            .removeNotesFromPinned(id = id)
    }
}