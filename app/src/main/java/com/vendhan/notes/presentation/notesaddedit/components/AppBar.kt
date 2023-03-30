package com.vendhan.notes.presentation.notesaddedit.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppBar(
    isPinned: Boolean,
    onBackPressed: () -> Unit,
    onClickPin: () -> Unit,
    onClickDeleteNotes: () -> Unit,
    onClickColorPicker: () -> Unit,
) {
    BackHandler {
        onBackPressed()
    }
    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        elevation = 4.dp,
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackPressed()
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "back_button",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    onClickPin()
                },
            ) {
                Icon(
                    imageVector = if (isPinned) Icons.Filled.PushPin else Icons.Outlined.PushPin,
                    contentDescription = "pin",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
            IconButton(
                onClick = {
                    onClickColorPicker()
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Palette,
                    contentDescription = "color",
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
            IconButton(
                onClick = {
                    onClickDeleteNotes()
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "delete",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        title = {},
    )
}
