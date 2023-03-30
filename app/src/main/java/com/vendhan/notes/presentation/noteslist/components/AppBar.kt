package com.vendhan.notes.presentation.noteslist.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.ViewAgenda
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.unit.dp
import com.vendhan.notes.presentation.noteslist.NotesListViewModel

@Composable
fun MyAppBar(
    isNotesEmpty: Boolean,
    notesListViewModel: NotesListViewModel,
) {
    TopAppBar(
        title = {
            Text(text = "Notes", style = MaterialTheme.typography.headlineMedium)
        },
        elevation = 4.dp,
        actions = {
            if (isNotesEmpty.not()) {
                IconButton(
                    onClick = {
                        notesListViewModel.listNotesAsGrid.value =
                            !notesListViewModel.listNotesAsGrid.value
                    },
                ) {
                    Icon(
                        imageVector = if (notesListViewModel.listNotesAsGrid.value) {
                            Icons.Rounded.ViewAgenda
                        } else {
                            Icons.Rounded.GridView
                        },
                        modifier = Modifier.size(24.dp),
                        contentDescription = "",
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primaryContainer)
            .takeOrElse {
                LocalContentColor.current
            },
    )
}
