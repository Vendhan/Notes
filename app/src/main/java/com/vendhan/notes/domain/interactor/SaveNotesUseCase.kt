package com.vendhan.notes.domain.interactor

import com.vendhan.notes.common.Result
import com.vendhan.notes.data.database.entity.NotesEntity
import com.vendhan.notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SaveNotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) {
    suspend fun saveNotes(notes: NotesEntity): Flow<Result<Boolean>> {
        return flow {
            emit(Result.Loading)
            try {
                notesRepository.addNotes(notes = notes)
                emit(
                    Result.Success(data = true)
                )
            } catch (e: Exception) {
                Result.Error(message = e.localizedMessage ?: "An error has occurred")
            }
        }
    }
}
