package ua.digi.diginote.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ua.digi.diginote.data.model.Note
import ua.digi.diginote.ui.screens.navigation.Screen
import ua.digi.diginote.ui.screens.viewmodels.MainViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel = MainViewModel()) {

    val notes by viewModel.notes.collectAsState(initial = emptyList())
    val itemIds by viewModel.itemIds.collectAsState()

    Scaffold(
        topBar = { MainAppBar() },
        floatingActionButton = { FAB { navController.navigate(Screen.Note.route) } }
    ) {
        Surface {
            if (notes.isEmpty()) {
                EmptyList()
            } else {
                NotesLazyColumn(notes, viewModel, itemIds, navController)
            }
        }
    }
}

@Composable
private fun MainAppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                Icons.Default.Home,
                contentDescription = ""
            )
        },
        title = {
            Text("Digi Note")
        }
    )
}

@Composable
fun FAB(onFabClick: () -> Unit) {
    ExtendedFloatingActionButton(
        icon = {
            Icon(Icons.Default.Add, contentDescription = "")
        },
        text = { Text("Create note") },
        onClick = { onFabClick.invoke() }
    )
}

@Composable
fun NotesLazyColumn(
    notes: List<Note>,
    viewModel: MainViewModel,
    itemIds: List<Long>,
    navController: NavController
) {
    val dialogOpen = remember { mutableStateOf(-1L) }

    val state: LazyListState = rememberLazyListState()
    LazyColumn(state = state) {
        itemsIndexed(notes) { index, note ->
            NoteCard(
                note = note,
                onClickDeleteItem = { dialogOpen.value = note.id },
//                onClickUpdateItem = { navController.navigate(Screen.Note.route + "/${note.id}?editMode=true") },
                onClickUpdateItem = { navController.navigate(Screen.Note.route + "/${note.id}") },
                onClickExpandItem = { viewModel.onItemClicked(index.toLong()) },
                expanded = itemIds.contains(index.toLong()),
            )
        }
    }

    if (dialogOpen.value != -1L) {
        ConfirmDeleteDialog(dialogOpen) { viewModel.deleteNoteById(dialogOpen.value) }
    }
}

@Composable
fun ConfirmDeleteDialog(dialogOpen: MutableState<Long>, onConfirmClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            dialogOpen.value = -1L
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmClick.invoke()
                    dialogOpen.value = -1L
                }
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    dialogOpen.value = -1L
                }
            ) {
                Text(text = "Cancel")
            }
        },
        title = {
            Text(text = "Delete confirmation")
        },
        text = {
            Text(text = "Delete Note?")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        shape = RoundedCornerShape(5.dp),
        backgroundColor = Color.White
    )
}

//@Preview(showBackground = true)
@Composable
fun NoteCard(
    note: Note,
    onClickDeleteItem: () -> Unit,
    onClickUpdateItem: () -> Unit,
    onClickExpandItem: () -> Unit,
    expanded: Boolean
) {
    Card(
        elevation = 9.dp, modifier = Modifier
            .fillMaxSize()
            .padding(all = 8.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClickExpandItem
            )
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.h5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                    modifier = Modifier.weight(1.0f)
                )
                Row {
                    IconButton(onClick = onClickUpdateItem) {
                        Icon(Icons.Default.Edit, contentDescription = "")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = onClickDeleteItem) {
                        Icon(Icons.Default.Delete, contentDescription = "")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
            ExpandableView(text = note.text, isExpanded = expanded)
        }
    }
}

@Composable
fun ExpandableView(text: String, isExpanded: Boolean) {

    val expandTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeIn(
            animationSpec = tween(300)
        )
    }
    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeOut(
            animationSpec = tween(300)
        )
    }

    AnimatedVisibility(
        visible = isExpanded,
        enter = expandTransition,
        exit = collapseTransition
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            style = MaterialTheme.typography.h6,
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
            softWrap = true
        )
    }
}

@Composable
fun EmptyList() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .alpha(0.4f)
                .align(Alignment.Center),
            text = "Notes list is empty. Click on '+' button to create a note."
        )
    }
}