package com.vendhan.notes.domain.interactor

import com.vendhan.notes.common.Result
import com.vendhan.notes.data.database.entity.NotesEntity
import com.vendhan.notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetNotesByIdUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) {
    suspend fun getNotesByID(id: Int): Flow<Result<NotesEntity>> {
        return flow {
            try {
                emit(Result.Loading)
                val note = notesRepository.getNotesByID(id = id)
                emit(Result.Success(data = note))
            } catch (e: Exception) {
                emit(Result.Error(message = e.localizedMessage ?: "An error has occurred"))
            }
        }
    }
}
