package com.vendhan.notes.presentation.notes_add_edit

import androidx.compose.ui.focus.FocusState

sealed class NotesAddOrEditEvent {
    data class EnteredTitle(val value: String) : NotesAddOrEditEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : NotesAddOrEditEvent()
    data class EnteredContent(val value: String) : NotesAddOrEditEvent()
    data class ChangeContentFocus(val focusState: FocusState) : NotesAddOrEditEvent()
    data class ChangeColor(val color: Int) : NotesAddOrEditEvent()
    object DeleteNote : NotesAddOrEditEvent()
    object PinNote : NotesAddOrEditEvent()
    object SaveNote : NotesAddOrEditEvent()
}
