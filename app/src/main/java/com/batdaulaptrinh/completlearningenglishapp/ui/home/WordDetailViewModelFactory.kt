package com.batdaulaptrinh.completlearningenglishapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository

class WordDetailViewModelFactory(val wordRepository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordDetailViewModel::class.java)) {
            return WordDetailViewModel(wordRepository) as T
        }
        throw IllegalArgumentException()
    }
}