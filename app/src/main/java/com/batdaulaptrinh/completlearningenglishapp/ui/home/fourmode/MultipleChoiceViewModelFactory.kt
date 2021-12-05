package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository

class MultipleChoiceViewModelFactory(val wordRepository: WordRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MultipleChoiceViewModel::class.java)) {
            return MultipleChoiceViewModel(wordRepository) as T
        }
        throw IllegalArgumentException()
    }
}