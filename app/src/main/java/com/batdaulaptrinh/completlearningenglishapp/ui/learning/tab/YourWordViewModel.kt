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

class YourWordViewModel(private val wordRepository: WordRepository) : ViewModel() {
    val listYourWord = MutableLiveData<List<MinimalWord>>()

    fun deleteFavouriteWord(_id: String) {
        wordRepository.deleteFavouriteWord(_id)
    }

    fun insertFavouriteWord(_id: String) {
        wordRepository.insertFavouriteWord(_id)
    }

    fun updateListWordWord() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("QUERY YOUR WORD VM", wordRepository.getMyWordList().size.toString())
            listYourWord.postValue(wordRepository.getMyWordList())
        }
    }

}