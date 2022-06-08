package com.vendhan.notes.common

sealed class UiEvent {
    data class ShowSnackBar(val message: String, val action: String) : UiEvent()
    object PopBackStack : UiEvent()
}
