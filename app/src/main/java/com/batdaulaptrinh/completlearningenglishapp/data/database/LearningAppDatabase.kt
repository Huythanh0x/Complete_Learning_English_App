package com.batdaulaptrinh.completlearningenglishapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.batdaulaptrinh.completlearningenglishapp.model.LearnedDate
import com.batdaulaptrinh.completlearningenglishapp.model.Word

@Database(entities = [Word::class, LearnedDate::class], version = 2)
abstract class LearningAppDatabase : RoomDatabase() {
    abstract val wordDao: WordDAO
    abstract val learnedDateDAO: LearnedDateDAO

    companion object {
        @Volatile
        private var INSTANCE: LearningAppDatabase? = null
        fun getInstance(context: Context): LearningAppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(context, LearningAppDatabase::class.java, "app_learning_db")
                            .createFromAsset("database/word_database.db")
                            .allowMainThreadQueries()
                            .build()
                }
                return instance
            }
        }
    }
}