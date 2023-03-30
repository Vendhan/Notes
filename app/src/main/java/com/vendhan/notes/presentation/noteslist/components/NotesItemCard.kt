@file:OptIn(ExperimentalMaterial3Api::class)

package com.vendhan.notes.presentation.noteslist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vendhan.notes.data.database.entity.NotesEntity

@Composable
fun NotesItemCard(
    modifier: Modifier = Modifier,
    notes: NotesEntity,
    navigateToDetailsScreen: (NotesEntity) -> Unit,
    maxHeight: Int = 260,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = maxHeight.dp)
            .clickable {
                navigateToDetailsScreen(notes)
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            if (notes.title.isNotEmpty()) {
                Text(
                    text = notes.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            if (notes.description.isNotEmpty()) {
                Text(
                    text = notes.description,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 10,
                )
            }
        }
    }
}
