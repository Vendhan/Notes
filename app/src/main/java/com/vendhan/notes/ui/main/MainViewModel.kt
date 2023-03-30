package com.vendhan.notes.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vendhan.notes.data.database.entity.NotesEntity
import com.vendhan.notes.domain.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
) : ViewModel() {

    private val _notes = MutableStateFlow<List<NotesEntity>>(emptyList())
    val notes: StateFlow<List<NotesEntity>>
        get() = _notes

    private val _pinnedNotes = MutableStateFlow<List<NotesEntity>>(emptyList())
    val pinnedNotes: StateFlow<List<NotesEntity>>
        get() = _pinnedNotes

    init {
        getAllPinnedNotes()
        getAllNotes()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            notesRepository.getAllNotes()
                .catch {
                }
                .collect {
                    _notes.value = it
                }
        }
    }

    private fun getAllPinnedNotes() {
        viewModelScope.launch {
            notesRepository.getAllPinnedNotes()
                .catch {
                }
                .collect {
                    _pinnedNotes.value = it
                }
        }
    }

    private fun setColorToNotes(id: String) {}
}
