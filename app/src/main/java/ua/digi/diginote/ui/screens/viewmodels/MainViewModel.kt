package ua.digi.diginote.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ua.digi.diginote.NoteApp
import ua.digi.diginote.data.model.Note

class MainViewModel : ViewModel() {

    private val repository = NoteApp.instance.repository

    val notes: Flow<List<Note>> = repository.notes

    fun deleteNoteById(value: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNoteById(value)
        }
    }


    private val itemsIdsList = MutableStateFlow(listOf<Long>())
    val itemIds: StateFlow<List<Long>>
        get() = itemsIdsList

    fun onItemClicked(item: Long) {
        itemsIdsList.value = itemsIdsList.value.toMutableList().also { list ->
            if (list.contains(item)) {
                list.remove(item)
            } else {
                list.add(item)
            }
        }
    }

}