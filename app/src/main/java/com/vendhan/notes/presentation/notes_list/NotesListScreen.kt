@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.vendhan.notes.presentation.notes_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.vendhan.notes.common.Result
import com.vendhan.notes.data.database.entity.NotesEntity
import com.vendhan.notes.presentation.notes_list.components.ExtendableFloatingActionButton
import com.vendhan.notes.presentation.notes_list.components.MyAppBar
import com.vendhan.notes.presentation.notes_list.components.NotesItemCard

@Composable
fun NotesListScreen(
    notesListViewModel: NotesListViewModel = hiltViewModel(),
    navigateToAddNotes: () -> Unit,
    navigateToDetailsScreen: (NotesEntity) -> Unit
) {
    val listState = rememberLazyListState()
    val gridState = rememberLazyGridState()
    val isNotesEmpty = rememberSaveable {
        mutableStateOf(true)
    }
    val showExtendedFab by
    remember(listState.firstVisibleItemIndex) {
        derivedStateOf {
            listState.firstVisibleItemIndex <= 1
        }
    }
    val showExtendedFab1 by
    remember(gridState.firstVisibleItemIndex) {
        derivedStateOf {
            gridState.firstVisibleItemIndex <= 0
        }
    }
    Scaffold(
        modifier = Modifier,
        topBar = {
            MyAppBar(
                isNotesEmpty = isNotesEmpty.value,
                notesListViewModel = notesListViewModel
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = SnackbarHostState()) { data ->
                Snackbar(snackbarData = data)
            }
        },
        floatingActionButton = {
            ExtendableFloatingActionButton(
                extended = showExtendedFab && showExtendedFab1,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add"
                    )
                },
                text = {
                    Text(
                        text = "New Notes"
                    )
                },
                onClick = {
                    navigateToAddNotes()
                }
            )
        },
    ) { paddingValues ->
        val lifecycleOwner = LocalLifecycleOwner.current
        val notes = notesListViewModel.notes
        val notesFlowLifecycleAware = remember(notes, lifecycleOwner) {
            notes.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
        }
        val notesList by notesFlowLifecycleAware.collectAsState(Result.Loading)

        when (val result = notesList) {
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
            Result.Initial -> {}
            Result.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
            is Result.Success -> {
                if (result.data.isEmpty()) {
                    isNotesEmpty.value = true
                    ShowEmptyListCard()
                } else {
                    isNotesEmpty.value = false
                    if (notesListViewModel.listNotesAsGrid.value) {
                        NotesGridScreenBody(
                            pinnedNotesList = result.data.filter { it.isPinned },
                            notesList = result.data.filter { !it.isPinned }
                                .sortedByDescending { it.timeStamp },
                            navigateToDetailsScreen = navigateToDetailsScreen,
                            state = gridState,
                            modifier = Modifier.padding(paddingValues)
                        )
                    } else
                        NotesListScreenBody(
                            pinnedNotesList = result.data.filter { it.isPinned },
                            notesList = result.data.filter { !it.isPinned }
                                .sortedByDescending { it.timeStamp },
                            navigateToDetailsScreen = navigateToDetailsScreen,
                            state = listState,
                            modifier = Modifier.padding(paddingValues)
                        )
                }
            }
        }
    }
}

@Composable
fun NotesListScreenBody(
    pinnedNotesList: List<NotesEntity>,
    notesList: List<NotesEntity>,
    modifier: Modifier = Modifier,
    state: LazyListState,
    navigateToDetailsScreen: (NotesEntity) -> Unit,
) {
    LazyColumn(
        state = state,
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (pinnedNotesList.isNotEmpty()) {
            item {
                PinnedNotesHeader()
            }
            items(
                key = { it.id },
                items = pinnedNotesList
            ) { pinnedNotes ->
                NotesItemCard(
                    notes = pinnedNotes,
                    navigateToDetailsScreen = navigateToDetailsScreen,
                )
            }
        }
        if (notesList.isNotEmpty()) {
            item {
                OtherNotesHeader()
            }
            items(
                key = { it.id },
                items = notesList
            ) { notes ->
                NotesItemCard(
                    notes = notes,
                    navigateToDetailsScreen = navigateToDetailsScreen
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}

@Composable
fun NotesGridScreenBody(
    pinnedNotesList: List<NotesEntity>,
    notesList: List<NotesEntity>,
    modifier: Modifier = Modifier,
    state: LazyGridState,
    navigateToDetailsScreen: (NotesEntity) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        state = state,
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (pinnedNotesList.isNotEmpty()) {
            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                PinnedNotesHeader()
            }
            items(
                pinnedNotesList
            ) { pinnedNotes ->
                NotesItemCard(
                    notes = pinnedNotes,
                    navigateToDetailsScreen = navigateToDetailsScreen
                )
            }
        }
        if (notesList.isNotEmpty()) {

            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                OtherNotesHeader()
            }
            items(notesList) { notes ->
                NotesItemCard(
                    notes = notes,
                    navigateToDetailsScreen = navigateToDetailsScreen
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(72.dp))
        }
    }
}

@Composable
fun ShowEmptyListCard() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Description,
                contentDescription = "empty_list",
                modifier = Modifier.size(
                    200.dp
                ),
                tint = MaterialTheme.colorScheme.primaryContainer
            )
            Text(
                text = "Your notes will appear here",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
fun OtherNotesHeader() {
    Text(
        text = "Others",
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(
            start = 16.dp,
            top = 16.dp,
            bottom = 8.dp
        )
    )
}

@Composable
fun PinnedNotesHeader() {
    Text(
        text = "Pinned",
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(
            start = 16.dp,
            top = 16.dp,
            bottom = 8.dp
        )
    )
}
