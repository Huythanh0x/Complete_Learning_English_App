package com.batdaulaptrinh.completlearningenglishapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.batdaulaptrinh.completlearningenglishapp.model.Word

@Dao
interface WordDAO {
    @Query("SELECT * FROM word_table")
    fun getAllWords(): LiveData<List<Word>>

    @Query("SELECT * FROM word_table WHERE en_word = :en_word")
    fun getWord(en_word: String): Word

    @Query("SELECT * FROM word_table WHERE en_word LIKE :en_word")
    fun getSuggestion(en_word: String): List<Word>
}