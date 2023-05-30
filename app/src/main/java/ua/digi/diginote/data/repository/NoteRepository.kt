package ua.digi.diginote.data.repository

import kotlinx.coroutines.flow.Flow
import ua.digi.diginote.data.datasource.local.NoteDao
import ua.digi.diginote.data.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    val notes: Flow<List<Note>> = noteDao.getAllNotes()

    fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    fun getNoteById(id: Long) = noteDao.getNoteById(id)
    fun deleteNoteById(value: Long) {
        noteDao.deleteNoteById(value)
    }

    fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }
}