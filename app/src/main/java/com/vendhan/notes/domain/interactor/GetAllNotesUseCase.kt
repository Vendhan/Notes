package com.vendhan.notes.domain.interactor

import com.vendhan.notes.common.Result
import com.vendhan.notes.data.database.entity.NotesEntity
import com.vendhan.notes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) {
    suspend fun getAllNotes(): Flow<Result<List<NotesEntity>>> {
        return flow {
            emit(Result.Loading)
            notesRepository.getAllNotes()
                .catch {
                    emit(Result.Error(message = "An error has occurred"))
                }
                .collect {
                    emit(Result.Success(data = it))
                }
        }
    }
}
