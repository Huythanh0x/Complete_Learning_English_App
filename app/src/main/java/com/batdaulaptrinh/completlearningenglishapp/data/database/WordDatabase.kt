package com.batdaulaptrinh.completlearningenglishapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.batdaulaptrinh.completlearningenglishapp.model.Word

@Database(entities = [Word::class], version = 2)
abstract class WordDatabase : RoomDatabase() {
    abstract val wordDao: WordDAO
    abstract val learnedDateDAO: LearnedDateDAO

    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null
        fun getInstance(context: Context): WordDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(context, WordDatabase::class.java, "word_database")
                            .createFromAsset("database/word_database.db")
                            .allowMainThreadQueries()
                            .build()
                }
                return instance
            }
        }
    }
}