package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository

class FlashCardViewModelFactory(val wordRepository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashCardViewModel::class.java)) {
            return FlashCardViewModel(wordRepository) as T
        }
        throw IllegalArgumentException()
    }
}