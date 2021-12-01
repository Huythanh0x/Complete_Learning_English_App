package com.batdaulaptrinh.completlearningenglishapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.batdaulaptrinh.completlearningenglishapp.model.Word

@Dao
interface WordDAO {
    @Query("SELECT * FROM word_table")
    fun getAllWords(): List<Word>

    @Query("SELECT * FROM word_table WHERE en_word = :en_word")
    suspend fun getWord(en_word: String): LiveData<Word>

    @Query("SELECT * FROM word_table WHERE en_word LIKE :en_word LIMIT 5")
    suspend fun getSuggestion(en_word: String): LiveData<List<Word>>

    @Query("SELECT * FROM word_table WHERE set_nth != null LIMIT :quantity")
    suspend fun getNewSet(quantity: Int): LiveData<List<Word>>

    @Update
    suspend fun updateWord(word: Word)

    @Query("SELECT * FROM word_table WHERE set_nth = :nTh")
    suspend fun getSetWord(nTh: Int): LiveData<List<Word>>
}