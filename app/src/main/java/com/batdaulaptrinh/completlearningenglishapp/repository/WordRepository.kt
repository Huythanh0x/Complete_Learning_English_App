package com.batdaulaptrinh.completlearningenglishapp.repository

import com.batdaulaptrinh.completlearningenglishapp.data.database.WordDAO
import com.batdaulaptrinh.completlearningenglishapp.model.MinimalWord
import com.batdaulaptrinh.completlearningenglishapp.model.Word


class WordRepository(private val wordDao: WordDAO) {
    fun getAllWords(): List<MinimalWord> {
        return wordDao.getAllWords()
    }

    suspend fun getNewSet(quantity: Int): List<Word> {
        return wordDao.getNewSet(quantity)
    }

    suspend fun updateWord(word: Word) {
        return wordDao.updateWord(word)
    }

    suspend fun getWord(_id: String): Word {
        return wordDao.getWord(_id)
    }

    suspend fun getSetWord(nTh: String): List<Word> {
        return wordDao.getSetWord(nTh)
    }

    fun getMyWordList(): List<MinimalWord> {
        return wordDao.getMyWordList()
    }

    fun getYourWordSortedListAZ(): List<MinimalWord> {
        return wordDao.getYourWordSortedListAZ()
    }

    fun getYourWordSortedListZA(): List<MinimalWord> {
        return wordDao.getYourWordSortedListZA()
    }

    fun getALlWordSortedListAZ(): List<MinimalWord> {
        return wordDao.getAllWordSortedListAZ()
    }

    fun getALlWordSortedListZA(): List<MinimalWord> {
        return wordDao.getAllWordSortedListZA()
    }

    fun insertFavouriteWord(wordId: String) {
        return wordDao.insertFavouriteWord(wordId)
    }

    fun deleteFavouriteWord(wordId: String) {
        return wordDao.deleteFavouriteWord(wordId)
    }

    fun getSearchYourWord(formattedString: String): List<MinimalWord> {
        return wordDao.getSearchYourWord(formattedString)
    }

    fun getSearchALlWord(formattedString: String): List<MinimalWord> {
        return wordDao.getSearchAllWord(formattedString)
    }

    fun getAllWordSortedListLevelASC(): List<MinimalWord> {
        return wordDao.getAllWordSortedListLevelASC()
    }

    fun getAllWordSortedListLevelDESC(): List<MinimalWord> {
        return wordDao.getAllWordSortedListLevelDESC()
    }

    fun getFakeSetWord(nTh: Int): List<Word> {
        return wordDao.getFakeSetWord(nTh)
    }

    fun getEntranceListWord(): List<MinimalWord>? {
        return wordDao.getEntranceListWord()
    }

    fun getNumberOfWord(): Int = wordDao.getNumberOfWord()

    fun getNumberOfLearnedWord():Int = wordDao.getNumberOfLearnedWord()
}