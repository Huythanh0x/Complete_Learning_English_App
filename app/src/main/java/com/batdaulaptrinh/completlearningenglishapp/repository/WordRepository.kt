package com.batdaulaptrinh.completlearningenglishapp.repository

import com.batdaulaptrinh.completlearningenglishapp.data.database.LearnedDateDAO
import com.batdaulaptrinh.completlearningenglishapp.data.database.WordDAO
import com.batdaulaptrinh.completlearningenglishapp.model.MinimalWord
import com.batdaulaptrinh.completlearningenglishapp.model.Word


class WordRepository(private val wordDao: WordDAO, private val leanedDateDAO: LearnedDateDAO) {
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

    fun insertFavouriteWord(wordId: String) {
        return wordDao.insertFavouriteWord(wordId)
    }

    fun deleteFavouriteWord(wordId: String) {
        return wordDao.deleteFavouriteWord(wordId)
    }
}