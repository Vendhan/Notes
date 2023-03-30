package com.vendhan.notes.presentation.noteslist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vendhan.notes.common.Result
import com.vendhan.notes.common.UiEvent
import com.vendhan.notes.data.database.entity.NotesEntity
import com.vendhan.notes.domain.interactor.GetAllNotesUseCase
import com.vendhan.notes.domain.interactor.SaveNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val saveNotesUseCase: SaveNotesUseCase
) : ViewModel() {

    private val _notes = MutableStateFlow<Result<List<NotesEntity>>>(Result.Loading)
    val notes: StateFlow<Result<List<NotesEntity>>>
        get() = _notes

    private val _pinnedNotes = MutableStateFlow<Result<List<NotesEntity>>>(Result.Loading)
    val pinnedNotes: StateFlow<Result<List<NotesEntity>>>
        get() = _pinnedNotes

    private val _uiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvent: Flow<UiEvent> = _uiEvent.asSharedFlow()

    val listNotesAsGrid = mutableStateOf(false)

    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            getAllNotesUseCase.getAllNotes()
                .collect {
                    _notes.value = it
                }
        }
    }

    fun addToList() {
        val note = NotesEntity(
            title = "Some Random Note",
            description = "It's the description of the notes",
            color = 0,
            isPinned = false,
        )
        viewModelScope.launch {
            saveNotesUseCase.saveNotes(notes = note)
        }
    }
}
