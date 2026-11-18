package com.example.typeconverterca.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.typeconverterca.data.NoteDatabase
import com.example.typeconverterca.data.NoteEntity
import com.example.typeconverterca.data.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository

    val notes: StateFlow<List<NoteEntity>>

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        notes = repository.allNotes
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.addNote(title, content, Date())
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }
}
