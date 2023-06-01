package ua.digi.diginote.ui.screens.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.digi.diginote.NoteApp
import ua.digi.diginote.data.model.Note
import java.util.*

class NoteViewModel : ViewModel() {

    private val repository = NoteApp.instance.repository

    val noteTitle = mutableStateOf("")
    val noteText = mutableStateOf("")
    val noteCreated = mutableStateOf<Date?>(null)
    val noteUpdated = mutableStateOf<Date?>(null)

    fun clearNoteTextFields() {
        noteTitle.value = ""
        noteText.value = ""
    }

    fun fillNoteTextFields(noteId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val note = repository.getNoteById(noteId)
                noteTitle.value = note.title
                noteText.value = note.text
                noteCreated.value = note.created
                noteUpdated.value = note.updated
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                }
            }
        }
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
        println(Date())
        viewModelScope.launch(Dispatchers.IO) {
            val note = repository.getNoteById(noteId)
            note.title = noteTitle.value
            note.text = noteText.value
            note.updated = Date()
            repository.updateNote(note)
        }
    }


}