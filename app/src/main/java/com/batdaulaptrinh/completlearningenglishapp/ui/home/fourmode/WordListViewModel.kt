package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batdaulaptrinh.completlearningenglishapp.model.Word
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository

class WordListViewModel(val wordRepository: WordRepository) : ViewModel() {
    val listWord = MutableLiveData<List<Word>>()

    fun getNewWordList(nTh: Int) {
        listWord.postValue(wordRepository.getFakeSetWord(nTh))
    }

    fun deleteFavouriteWord(_id: String) {
        wordRepository.deleteFavouriteWord(_id)
    }

    fun insertFavouriteWord(_id: String) {
        wordRepository.insertFavouriteWord(_id)
    }
}