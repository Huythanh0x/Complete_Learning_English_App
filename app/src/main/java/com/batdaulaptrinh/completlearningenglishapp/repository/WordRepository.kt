package com.batdaulaptrinh.completlearningenglishapp.repository

import com.batdaulaptrinh.completlearningenglishapp.data.database.WordDAO
import com.batdaulaptrinh.completlearningenglishapp.model.Word


class WordRepository(private val wordDao: WordDAO) {
    val allWords = wordDao.getAllWords()

    fun getWord(en_word: String): Word {
        return wordDao.getWord(en_word)
    }

    fun getSuggestion(en_word: String): List<Word> {
        return wordDao.getSuggestion(en_word)
    }
}