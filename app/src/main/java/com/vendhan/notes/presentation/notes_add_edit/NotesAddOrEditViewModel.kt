package com.vendhan.notes.presentation.notes_add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vendhan.notes.common.Result
import com.vendhan.notes.common.UiEvent
import com.vendhan.notes.data.database.entity.NotesEntity
import com.vendhan.notes.domain.interactor.*
import com.vendhan.notes.presentation.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesAddOrEditViewModel @Inject constructor(
    private val getNotesByIdUseCase: GetNotesByIdUseCase,
    private val saveNotesUseCase: SaveNotesUseCase,
    private val pinNotesUseCase: PinNotesUseCase,
    private val unPinNotesUseCase: UnPinNotesUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase,
    private val updateNotesUseCase: UpdateNotesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val notesID = mutableStateOf(-1)
    val titleFieldState = mutableStateOf("")
    val descriptionFieldState = mutableStateOf("")
    val isPinned = mutableStateOf(false)
    val colorState = mutableStateOf(-1)

    private val _loadNoteState = mutableStateOf<Result<NotesEntity>>(Result.Initial)
    val loadNoteState: State<Result<NotesEntity>>
        get() = _loadNoteState

    private val _uiEvent: Channel<UiEvent> = Channel()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    var notesChanged: Boolean = false

    init {
        savedStateHandle.get<Int>(Constants.NOTE_ID_KEY)?.let { noteId ->
            notesID.value = noteId
            if (noteId != -1) {
                getNotesByID(noteId)
            }
        }
    }

    private fun getNoteID(): Int {
        return notesID.value
    }

    private fun getNotesByID(id: Int) {
        viewModelScope.launch {
            getNotesByIdUseCase
                .getNotesByID(id = id)
                .collect { result ->
                    if (result is Result.Success) {
                        titleFieldState.value = result.data.title
                        descriptionFieldState.value = result.data.description
                        colorState.value = result.data.color
                        isPinned.value = result.data.isPinned
                    }
                    _loadNoteState.value = result
                }
        }
    }

    fun onEvent(event: NotesAddOrEditEvent) {
        when (event) {
            is NotesAddOrEditEvent.ChangeColor -> TODO()
            is NotesAddOrEditEvent.ChangeContentFocus -> TODO()
            is NotesAddOrEditEvent.ChangeTitleFocus -> TODO()
            is NotesAddOrEditEvent.DeleteNote -> {
                viewModelScope.launch {
                    deleteNotesUseCase
                        .deleteNotes(id = getNoteID())
                }
            }
            is NotesAddOrEditEvent.EnteredContent -> TODO()
            is NotesAddOrEditEvent.EnteredTitle -> TODO()
            is NotesAddOrEditEvent.PinNote -> {
                viewModelScope.launch {
                    if (isPinned.value.not())
                        pinNotesUseCase
                            .pinNotes(id = getNoteID())
                    else
                        unPinNotesUseCase
                            .unPinNotes(id = getNoteID())
                }
            }
            is NotesAddOrEditEvent.SaveNote -> {
                viewModelScope.launch {
                    if (loadNoteState.value is Result.Success || getNoteID() != -1) {
                        if (notesChanged)
                            updateNotesUseCase
                                .updateNotes(
                                    NotesEntity(
                                        id = getNoteID(),
                                        title = titleFieldState.value,
                                        description = descriptionFieldState.value,
                                        color = colorState.value,
                                        timeStamp = getTimeStamp(),
                                        isPinned = isPinned.value
                                    )
                                )
                    } else {
                        if (titleFieldState.value.isNotEmpty() || descriptionFieldState.value.isNotEmpty())
                            saveNotesUseCase
                                .saveNotes(
                                    NotesEntity(
                                        title = titleFieldState.value,
                                        description = descriptionFieldState.value,
                                        color = colorState.value,
                                        timeStamp = getTimeStamp(),
                                        isPinned = isPinned.value
                                    )
                                )
                                .collect { result ->
                                    if (result is Result.Success) {
                                    }
                                }
                    }
                }
            }
        }
    }

    private fun getTimeStamp(): String {
        return System.currentTimeMillis().toString()
    }
}
