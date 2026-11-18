package com.example.typeconverterca.data

import kotlinx.coroutines.flow.Flow
import java.util.Date

class NoteRepository(private val noteDao: NoteDao) {
    // Single Source of Truth: reactive flow from database
    val allNotes: Flow<List<NoteEntity>> = noteDao.getAllNotes()

    suspend fun addNote(title: String, content: String, createdAt: Date) {
        val note = NoteEntity(title = title, content = content, createdAt = createdAt)
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(note: NoteEntity) {
        noteDao.deleteNote(note)
    }
}
