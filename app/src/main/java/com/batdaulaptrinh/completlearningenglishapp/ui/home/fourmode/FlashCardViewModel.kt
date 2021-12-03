package com.batdaulaptrinh.completlearningenglishapp.ui.home.fourmode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batdaulaptrinh.completlearningenglishapp.model.Word
import com.batdaulaptrinh.completlearningenglishapp.repository.WordRepository


class FlashCardViewModel(val wordRepository: WordRepository) : ViewModel() {
    val listWord = MutableLiveData<List<Word>>()

    fun getSetWordNth(nTh: Int) {
        listWord.postValue(wordRepository.getFakeSetWord(nTh))
    }
}