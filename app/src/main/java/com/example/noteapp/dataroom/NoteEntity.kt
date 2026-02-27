package com.example.noteapp.dataroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DataBaseHandler.NOTE_TABLE)
data class NoteEntity(

    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo val title : String ,
    @ColumnInfo val detail : String,
    @ColumnInfo val deleteState : String,
    @ColumnInfo val date : String
)
