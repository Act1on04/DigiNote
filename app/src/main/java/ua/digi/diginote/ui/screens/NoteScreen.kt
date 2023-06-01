package ua.digi.diginote.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ua.digi.diginote.data.model.Note
import ua.digi.diginote.ui.screens.viewmodels.NoteViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    navController: NavController,
    noteId: Long?,
//    note: Note?,
    viewModel: NoteViewModel = NoteViewModel(),
//    editMode: Boolean = false, // Новое состояние для определения режима экрана
) {
//    val noteId = rememberNavController().currentBackStackEntry?.arguments?.getLong("noteId")
    println("---------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>> noteId")
    println(noteId)
    val editMode: Boolean = noteId != null
//    val NoteIdL = noteId?.toLong()

    Scaffold(topBar = { }) {

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ScreenTitle()
            Spacer(modifier = Modifier.height(32.dp))
            NoteTitle(viewModel)
            Spacer(modifier = Modifier.height(32.dp))
            NoteText(viewModel)
            Spacer(modifier = Modifier.weight(1.0f))
            Buttons(
                onClearClick = { viewModel.clearNoteTextFields() },
                onAddClick = {
                    if (editMode) {
                        noteId?.let { it1 -> viewModel.updateNote(it1) }
                    } else {
                        viewModel.addNote()
                    }
                    navController.navigateUp()
                },
                editMode = editMode // Передача значения режима экрана в функцию Buttons
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


@Composable
fun ScreenTitle() {
    Text(text = "Add Note", textAlign = TextAlign.Center, style = MaterialTheme.typography.h4)
}

@Composable
fun NoteTitle(viewModel: NoteViewModel) {

    val text = viewModel.noteTitle.value

    OutlinedTextField(
        value = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onValueChange = { viewModel.noteTitle.value = it },
        label = { Text("Enter note title...") },
        singleLine = true,
        maxLines = 1,
        textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold)
    )
}

@Composable
fun NoteText(viewModel: NoteViewModel) {

    val text = viewModel.noteText.value

    OutlinedTextField(
        value = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onValueChange = { viewModel.noteText.value = it },
        label = { Text("Enter note text...") },
        singleLine = false,
        maxLines = 10,
        textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold)
    )
}


@Composable
fun Buttons(
    onClearClick: () -> Unit,
    onAddClick: () -> Unit,
    editMode: Boolean // Новый параметр для определения режима экрана
) {
    Row {
        if (editMode) { // Если режим редактирования, отображаем только кнопку "Save Note"
            Button(
                onClick = onAddClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f)
                    .padding(horizontal = 8.dp)
            ) {
                Text("Save Note")
            }
        } else { // Если режим добавления, отображаем кнопки "Clear" и "Add Note"
            Button(
                onClick = onClearClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f)
                    .padding(horizontal = 8.dp)
            ) {
                Text("Clear")
            }
            Button(
                onClick = onAddClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f)
                    .padding(horizontal = 8.dp)
            ) {
                Text("Add Note")
            }
        }
    }
}