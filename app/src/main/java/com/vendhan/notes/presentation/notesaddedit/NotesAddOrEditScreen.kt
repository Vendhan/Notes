package com.vendhan.notes.presentation.notesaddedit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vendhan.notes.common.Result
import com.vendhan.notes.presentation.notesaddedit.components.AppBar
import com.vendhan.notes.presentation.notesaddedit.components.HintTextField
import com.vendhan.notes.presentation.noteslist.NotesListViewModel
import com.vendhan.notes.presentation.noteslist.components.ExtendableFloatingActionButton
import com.vendhan.notes.ui.theme.Amber
import com.vendhan.notes.ui.theme.BabyBlue
import com.vendhan.notes.ui.theme.BlueGrey
import com.vendhan.notes.ui.theme.Green
import com.vendhan.notes.ui.theme.Lavender
import com.vendhan.notes.ui.theme.LightGreen
import com.vendhan.notes.ui.theme.LightOrange
import com.vendhan.notes.ui.theme.LightPink
import com.vendhan.notes.ui.theme.RedOrange
import com.vendhan.notes.ui.theme.Teal

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun NotesAddOrEditScreen(
    notesListViewModel: NotesListViewModel,
    notesAddOrEditViewModel: NotesAddOrEditViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            AppBar(
                isPinned = notesAddOrEditViewModel.isPinned.value,
                onBackPressed = {
                    notesAddOrEditViewModel.onEvent(NotesAddOrEditEvent.SaveNote)
                    popBackStack()
                },
                onClickPin = {
                    notesAddOrEditViewModel.notesChanged = true
                    notesAddOrEditViewModel.isPinned.value = !notesAddOrEditViewModel.isPinned.value
                    // notesAddOrEditViewModel.onEvent(NotesAddOrEditEvent.PinNote)
                },
                onClickDeleteNotes = {
                    notesAddOrEditViewModel.onEvent(NotesAddOrEditEvent.DeleteNote)
                    popBackStack()
                },
                onClickColorPicker = {},
            )
        },
        snackbarHost = {},
        floatingActionButton = {
            ExtendableFloatingActionButton(
                extended = true,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = "Save",
                    )
                },
                text = {
                    Text(
                        text = "Save",
                    )
                },
                onClick = {
                    notesAddOrEditViewModel.onEvent(NotesAddOrEditEvent.SaveNote)
                    popBackStack()
                },
            )
        },
    ) {
        when (val result = notesAddOrEditViewModel.loadNoteState.value) {
            is Result.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
            is Result.Error -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = result.message,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }
            else -> {
                NotesAddOrEditScreenBody(
                    modifier = Modifier
                        .padding(it),
                    notesAddOrEditViewModel = notesAddOrEditViewModel,
                )
            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun NotesAddOrEditScreenBody(
    modifier: Modifier,
    notesAddOrEditViewModel: NotesAddOrEditViewModel,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .bringIntoViewRequester(bringIntoViewRequester),
    ) {
        HintTextField(
            value = notesAddOrEditViewModel.titleFieldState.value,
            onChange = {
                notesAddOrEditViewModel.notesChanged = true
                notesAddOrEditViewModel.titleFieldState.value = it
            },
            textStyle = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
            ),
            hint = "Title",
            modifier = Modifier.fillMaxWidth(),
            bringIntoViewRequester = bringIntoViewRequester,
        )
        Spacer(modifier = Modifier.height(16.dp))
        HintTextField(
            value = notesAddOrEditViewModel.descriptionFieldState.value,
            onChange = {
                notesAddOrEditViewModel.notesChanged = true
                notesAddOrEditViewModel.descriptionFieldState.value = it
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
            hint = "Description",
            modifier = Modifier
                .weight(1F)
                .fillMaxSize(),
            bringIntoViewRequester = bringIntoViewRequester,
        )
    }
}

@Composable
fun BottomSheetColorPicker(
    notesAddOrEditViewModel: NotesAddOrEditViewModel,
) {
    Column {
        Text(
            text = "Color",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(all = 16.dp),
        )
        val colors: List<Color> =
            listOf(
                MaterialTheme.colorScheme.surfaceVariant,
                RedOrange,
                LightGreen,
                BabyBlue,
                LightPink,
                Lavender,
                Teal,
                LightOrange,
                BlueGrey,
                Green,
                Amber,
            )
        LazyRow(
            modifier = Modifier.padding(16.dp),
        ) {
            itemsIndexed(colors) { selected: Int, color: Color ->
                val value = if (notesAddOrEditViewModel.colorState.value > 0) {
                    colors[notesAddOrEditViewModel.colorState.value.plus(1)]
                } else {
                    colors[0]
                }
                ColorPickerItem(
                    color = color,
                    selectedValue = value,
                    onClickColor = {
                        notesAddOrEditViewModel.notesChanged = true
                        notesAddOrEditViewModel.colorState.value = selected
                    },
                )
            }
        }
    }
}

@Composable
fun ColorPickerItem(
    color: Color,
    selectedValue: Color,
    onClickColor: (Color) -> Unit,
) {
    if (color != selectedValue) {
        Box(
            modifier = Modifier
                .padding(end = 16.dp)
                .selectable(
                    selected = (color == selectedValue),
                    onClick = {
                        onClickColor(color)
                    },
                )
                .size(64.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    color = color,
                ),
        )
    }
    if (color == selectedValue) {
        Box(
            modifier = Modifier
                .padding(end = 16.dp)
                .size(66.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(
                    border = BorderStroke(
                        width = 2.dp,
                        brush = SolidColor(MaterialTheme.colorScheme.primary),
                    ),
                    shape = RoundedCornerShape(24.dp),
                ),
        ) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
                    .align(Alignment.Center),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = .8F),
            )
        }
    }
}
