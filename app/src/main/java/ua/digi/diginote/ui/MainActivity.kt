package ua.digi.diginote.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import ua.digi.diginote.ui.screens.navigation.NavSetup
import ua.digi.diginote.ui.screens.viewmodels.SplashViewModel
import ua.digi.diginote.ui.theme.DigiNoteTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = SplashViewModel()

        installSplashScreen().setKeepOnScreenCondition { viewModel.isLoading.value }

        setContent {
            DigiNoteTheme {
                NavSetup()
            }
        }
    }
}