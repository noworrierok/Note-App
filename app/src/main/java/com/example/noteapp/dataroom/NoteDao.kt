package com.example.noteapp.dataroom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert
    fun insertNote(user: NoteEntity) : Long

    @Query("SELECT * FROM ${DataBaseHandler.NOTE_TABLE} WHERE ${DataBaseHandler.NOTE_DELETE_STATE} = :state")
    fun getNoteForRecycler(state : String) : List<NoteEntity>

    @Query("UPDATE ${DataBaseHandler.NOTE_TABLE} SET " +
            "${DataBaseHandler.NOTE_DELETE_STATE} = :state WHERE ${DataBaseHandler.NOTE_ID} = :id")
    fun editNote(id: Int, state: String): Int

    @Update
    fun updateNote(note: NoteEntity): Int

    @Query("SELECT * FROM ${DataBaseHandler.NOTE_TABLE} WHERE id = :id")
    fun getNoteById(id: Int): NoteEntity

    @Query("DELETE FROM ${DataBaseHandler.NOTE_TABLE} WHERE id = :id")
    fun deleteNoteById(id: Int): Int






}