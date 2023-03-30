package com.vendhan.notes.domain.interactor

import com.vendhan.notes.domain.repository.NotesRepository
import javax.inject.Inject

class PinNotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) {
    suspend fun pinNotes(id: Int) {
        notesRepository
            .addNotesToPinned(id = id)
    }
}
