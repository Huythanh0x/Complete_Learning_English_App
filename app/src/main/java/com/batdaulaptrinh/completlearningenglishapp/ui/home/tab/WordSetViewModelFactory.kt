package com.batdaulaptrinh.completlearningenglishapp.ui.home.tab

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import java.lang.IllegalArgumentException

class WordSetViewModelFactory(val application: Application, val wordRepository: WordRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WordSetViewModel::class.java)){
            return WordSetViewModel(application, wordRepository) as T
        }
        throw IllegalArgumentException()
    }
}