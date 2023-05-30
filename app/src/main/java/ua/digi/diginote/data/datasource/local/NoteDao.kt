package ua.digi.diginote.data.datasource.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ua.digi.diginote.data.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes() : Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNote(note: Note)

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Long): Note

    @Query("DELETE FROM notes WHERE id=:id")
    fun deleteNoteById(id: Long)

    @Update
    fun updateNote(note: Note)
}