package com.batdaulaptrinh.completlearningenglishapp.ui.home.tab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.batdaulaptrinh.completlearningenglishapp.data.sharedPreferences.SharePreferencesProvider
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository

class WordSetViewModel(application: Application, val wordRepository: WordRepository) :
    AndroidViewModel(application) {
    val sharePreferencesProvider = SharePreferencesProvider(application.baseContext)
    fun getNumberOfWord(): Int = wordRepository.getNumberOfWord()
    fun getNumberOfLearnedWord():Int = wordRepository.getNumberOfLearnedWord()
}