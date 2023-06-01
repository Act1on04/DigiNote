package ua.digi.diginote.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ua.digi.diginote.NoteApp
import ua.digi.diginote.ui.screens.MainScreen
import ua.digi.diginote.ui.screens.NoteScreen
import ua.digi.diginote.ui.screens.navigation.Screen

@Composable
fun NavSetup() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Main.route ) {
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.Note.route) { NoteScreen(navController, null) }
        composable(
            route = "${Screen.Note.route}/{${Screen.Note.argumentNoteId}}",
            arguments = listOf(navArgument(Screen.Note.argumentNoteId) { type = NavType.LongType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val noteId = arguments.getLong(Screen.Note.argumentNoteId)
            NoteScreen(navController = navController, noteId)
        }
    }

}