package ua.digi.diginote.ui.screens.navigation

sealed class Screen(val route: String) {
    object Main: Screen("main")
    object Note: Screen("note")
}