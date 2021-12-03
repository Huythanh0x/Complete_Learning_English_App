package com.batdaulaptrinh.completlearningenglishapp.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.batdaulaptrinh.completlearningenglishapp.model.MinimalWord
import com.batdaulaptrinh.completlearningenglishapp.model.Word

@Dao
interface WordDAO {
    @Query("SELECT _id,en_word,api_uk,api_us,mp3_uk,mp3_us,is_favourite,add_date FROM word_table")
    fun getAllWords(): List<MinimalWord>

    @Query("SELECT * FROM word_table WHERE set_nth != null LIMIT :quantity")
    fun getNewSet(quantity: Int): List<Word>

    @Query("SELECT _id,en_word,api_uk,api_us,mp3_uk,mp3_us,is_favourite,add_date FROM word_table ORDER BY en_word ASC")
    fun getAllWordSortedListAZ(): List<MinimalWord>

    @Query("SELECT _id,en_word,api_uk,api_us,mp3_uk,mp3_us,is_favourite,add_date FROM word_table ORDER BY en_word DESC")
    fun getAllWordSortedListZA(): List<MinimalWord>

    @Query("SELECT _id,en_word,api_uk,api_us,mp3_uk,mp3_us,is_favourite,add_date FROM word_table WHERE is_favourite = 1 ORDER BY en_word ASC")
    fun getYourWordSortedListAZ(): List<MinimalWord>

    @Query("SELECT _id,en_word,api_uk,api_us,mp3_uk,mp3_us,is_favourite,add_date FROM word_table WHERE is_favourite = 1 ORDER BY en_word DESC")
    fun getYourWordSortedListZA(): List<MinimalWord>

    @Query("SELECT _id,en_word,api_uk,api_us,mp3_uk,mp3_us,is_favourite,add_date FROM word_table WHERE en_word LIKE '' || :formattedString || '%'")
    fun getSearchAllWord(formattedString: String): List<MinimalWord>

    @Query("SELECT _id,en_word,api_uk,api_us,mp3_uk,mp3_us,is_favourite,add_date FROM word_table WHERE en_word LIKE '' || :formattedString || '%' and is_favourite = 1")
    fun getSearchYourWord(formattedString: String): List<MinimalWord>

    @Update
    fun updateWord(word: Word)

    @Query("SELECT * FROM word_table WHERE _id = :id")
    fun getWord(id: String): Word

    @Query("SELECT * FROM word_table WHERE is_favourite = 1")
    fun getMyWordList(): List<MinimalWord>

    @Query("SELECT * FROM word_table WHERE set_nth = :setNTh")
    fun getSetWord(setNTh: String): List<Word>

    @Query("UPDATE word_table SET is_favourite = 1 where _id = :wordId")
    fun insertFavouriteWord(wordId: String)

    @Query("UPDATE word_table SET is_favourite = 0 where _id = :wordId")
    fun deleteFavouriteWord(wordId: String)

    @Query("SELECT _id,en_word,api_uk,api_us,mp3_uk,mp3_us,is_favourite,add_date FROM word_table WHERE is_favourite = 1 ORDER BY cefr ASC")
    fun getAllWordSortedListLevelASC(): List<MinimalWord>

    @Query("SELECT _id,en_word,api_uk,api_us,mp3_uk,mp3_us,is_favourite,add_date FROM word_table WHERE is_favourite = 1 ORDER BY cefr DESC")
    fun getAllWordSortedListLevelDESC(): List<MinimalWord>
}