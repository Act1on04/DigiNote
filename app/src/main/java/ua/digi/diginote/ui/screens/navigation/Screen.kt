package ua.digi.diginote.ui.screens.navigation

import androidx.navigation.NavController

sealed class Screen(val route: String) {
    object Main: Screen("main")
//    object Note: Screen("note")
    object Note {
        const val route = "note"
        const val argumentNoteId = "noteId"
    }

}