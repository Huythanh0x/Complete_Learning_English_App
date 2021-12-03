package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository

class WordListViewModelFactory(val wordRepository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordListViewModel::class.java)) {
            return WordListViewModel(wordRepository) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}