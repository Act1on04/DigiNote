package ua.digi.diginote

import android.app.Application
import ua.digi.diginote.data.datasource.local.NotesDatabase
import ua.digi.diginote.data.repository.NoteRepository

class NoteApp : Application() {

    private val database by lazy {
        NotesDatabase.getDatabase(this)
    }

    val repository by lazy {
        NoteRepository(database.noteDao())
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: NoteApp
            private set
    }


}