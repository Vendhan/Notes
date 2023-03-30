@file:OptIn(ExperimentalComposeUiApi::class)

package com.vendhan.notes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vendhan.notes.presentation.notesaddedit.NotesAddOrEditScreen
import com.vendhan.notes.presentation.noteslist.NotesListScreen
import com.vendhan.notes.presentation.noteslist.NotesListViewModel
import com.vendhan.notes.presentation.utils.Constants

@Composable
fun NotesNavigationHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.NotesListScreen
    ) {
        composable(
            route = Screen.NotesListScreen
        ) {
            NotesListScreen(
                navigateToAddNotes = {
                    navController.navigate(
                        route = Screen.NotesAddOrEditScreen
                    )
                },
                navigateToDetailsScreen = { notes ->
                    navController.navigate(
                        route = Screen.NotesAddOrEditScreen + "?${Constants.NOTE_ID_KEY}=${notes.id}"
                    )
                }
            )
        }
        composable(
            route = Screen.NotesAddOrEditScreen + "?${Constants.NOTE_ID_KEY}={${Constants.NOTE_ID_KEY}}",
            arguments = listOf(
                navArgument(name = Constants.NOTE_ID_KEY) {
                    this.type = NavType.IntType
                    this.defaultValue = -1
                    this.nullable = false
                }
            )
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screen.NotesListScreen)
            }
            val notesListViewModel: NotesListViewModel = hiltViewModel(parentEntry)
            NotesAddOrEditScreen(
                notesListViewModel = notesListViewModel,
                popBackStack = {
                    keyboardController?.hide()
                    navController.popBackStack()
                }
            )
        }
    }
}
