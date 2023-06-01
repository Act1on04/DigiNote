package ua.digi.diginote.ui.screens.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ua.digi.diginote.NoteApp
import ua.digi.diginote.data.model.Note
import java.util.*

class NoteViewModel : ViewModel() {

    private val repository = NoteApp.instance.repository

    val noteTitle = mutableStateOf("")
    val noteText = mutableStateOf("")

    fun clearNoteTextFields() {
        noteTitle.value = ""
        noteText.value = ""
    }

    fun addNote() {
        val date = Date()

        val note = Note(
            title = noteTitle.value,
            text = noteText.value,
            created = date,
            updated = date
        )

        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

    fun updateNote(noteId: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            val note = repository.getNoteById(noteId)
            note.title = noteTitle.value
            note.text = noteText.value
            note.updated = Date()
            repository.updateNote(note)
        }
    }


}