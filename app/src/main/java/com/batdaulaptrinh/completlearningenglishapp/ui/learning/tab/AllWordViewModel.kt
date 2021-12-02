package com.batdaulaptrinh.completlearningenglishapp.ui.learning.tab

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batdaulaptrinh.completlearningenglishapp.model.MinimalWord
import com.batdaulaptrinh.completlearningenglishapp.model.Word
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllWordViewModel(private val wordRepository: WordRepository) : ViewModel() {
    val allWords = MutableLiveData<List<MinimalWord>>()

    fun updateListWordWord() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("QUERY YOUR WORD VM", wordRepository.getMyWordList().size.toString())
            allWords.postValue(wordRepository.getAllWords())
        }
    }

    fun deleteFavouriteWord(_id: String) {
        wordRepository.deleteFavouriteWord(_id)
    }

    fun insertFavouriteWord(_id: String) {
        wordRepository.insertFavouriteWord(_id)
    }
}