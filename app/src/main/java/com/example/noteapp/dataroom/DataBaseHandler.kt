package com.example.noteapp.dataroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NoteEntity::class],
    version = DataBaseHandler.DATABASE_VERSION,
)

abstract class DataBaseHandler : RoomDatabase() {


    abstract fun noteDao(): NoteDao


    companion object {

        private const val DATABASE_NAME = "main_database"
        const val DATABASE_VERSION = 1
        const val NOTE_TABLE = "noteTable"

        const val NOTE_ID = "id"
        const val NOTE_TITLE = "title"
        const val NOTE_DELETE_STATE = "deleteState"

        const val TRUE_DELETE = "1"   // اونایی که حذف شدن رو نمایش میده
        const val FALSE_DELETE = "0"  // لیست نرمال رو نمایش میده

        private var INSTANCE: DataBaseHandler? = null

        fun getDataBase(context: Context): DataBaseHandler {

            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder(
                    context,
                    DataBaseHandler::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!
        }


    }
}