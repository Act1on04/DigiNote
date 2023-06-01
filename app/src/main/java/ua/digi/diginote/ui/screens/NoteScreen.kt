package ua.digi.diginote.ui.screens

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ua.digi.diginote.ui.screens.viewmodels.NoteViewModel
import ua.digi.diginote.ui.theme.Blue500
import ua.digi.diginote.ui.theme.Blue700
import java.util.*

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
//    println("---------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>> noteId")
//    println(noteId)
    val editMode: Boolean = noteId != null
    noteId?.let { viewModel.fillNoteTextFields(it) }

    Scaffold(topBar = { }) {

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ScreenTitle(viewModel, editMode)
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


@SuppressLint("SimpleDateFormat")
@Composable
fun ScreenTitle(viewModel: NoteViewModel, editMode: Boolean) {
    val offset = Offset(5.0f, 10.0f)
    val style = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        letterSpacing = 0.25.sp,
        shadow = Shadow(
            color = Color.DarkGray, offset = offset, blurRadius = 5f
        )
    )
    if (editMode) {
        Text(text = "Edit Note", textAlign = TextAlign.Center, color = Blue700, style = style)
        if (viewModel.noteCreated.value != null && viewModel.noteUpdated.value != null) {
            val sdf = SimpleDateFormat("EEE, MMM d, yyyy  HH:mm")
            //            SimpleDateFormat("dd.MM.yyyy HH:mm")
            Text(
                text = "Created: " + sdf.format(viewModel.noteCreated.value), color = Blue500,
                textAlign = TextAlign.Left, style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = "Last updated: " + sdf.format(viewModel.noteUpdated.value), color = Blue500,
                textAlign = TextAlign.Left, style = MaterialTheme.typography.subtitle1
            )
        }
    } else
        Text(text = "Add Note", textAlign = TextAlign.Center, color = Blue700, style = style)

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