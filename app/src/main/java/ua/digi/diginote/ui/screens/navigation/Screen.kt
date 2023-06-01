package ua.digi.diginote.ui.screens.navigation

import androidx.navigation.NavController

sealed class Screen(val route: String) {
    object Main: Screen("main")
//    object Note(val note: Note): Screen("note")
    object Note {
        const val route = "note"
        const val argumentNoteId = "noteId"

        fun createRoute(noteId: Long): String {
            return "$route/{$argumentNoteId}"
        }

        fun navigateToNoteScreen(navController: NavController, noteId: Long) {
            navController.navigate(createRoute(noteId))
        }
    }

}