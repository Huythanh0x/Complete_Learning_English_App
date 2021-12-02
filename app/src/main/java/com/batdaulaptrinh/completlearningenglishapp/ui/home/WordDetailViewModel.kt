package com.batdaulaptrinh.completlearningenglishapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batdaulaptrinh.completlearningenglishapp.model.Word
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordDetailViewModel(val wordRepository: WordRepository) : ViewModel() {
    val fullWord = MutableLiveData<Word>()
    fun getWord(_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fullWord.postValue(wordRepository.getWord(_id))
        }
    }
}