package com.batdaulaptrinh.completlearningenglishapp.ui.learning.tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import java.lang.IllegalArgumentException

class AllWordViewModelFactory(private val wordRepository: WordRepository) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AllWordViewModel::class.java)){
            return AllWordViewModel(wordRepository) as T
        }else{
            throw IllegalArgumentException()
        }
    }
}