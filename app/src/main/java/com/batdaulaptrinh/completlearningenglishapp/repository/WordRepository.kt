package com.batdaulaptrinh.completlearningenglishapp.repository

import androidx.lifecycle.LiveData
import com.batdaulaptrinh.completlearningenglishapp.data.database.LearnedDateDAO
import com.batdaulaptrinh.completlearningenglishapp.data.database.WordDAO
import com.batdaulaptrinh.completlearningenglishapp.model.Word


class WordRepository(private val wordDao: WordDAO, private val leanedDateDAO: LearnedDateDAO) {
    val allWords = wordDao.getAllWords()
    val allLearnedDate = leanedDateDAO.getAllLearnedDate()

    suspend fun getWord(en_word: String): LiveData<Word> {
        return wordDao.getWord(en_word)
    }

    suspend fun getSuggestion(en_word: String): LiveData<List<Word>> {
        return wordDao.getSuggestion(en_word)
    }

    suspend fun getNewSet(quantity: Int): LiveData<List<Word>> {
        return wordDao.getNewSet(quantity)
    }

    suspend fun updateWord(word: Word) {
        return wordDao.updateWord(word)
    }

    suspend fun getSetWord(nTh: Int): LiveData<List<Word>> {
        return wordDao.getSetWord(nTh)
    }
}